package com.linchi.payments.paymentsapi.service.payments.impl;
import com.linchi.payments.paymentsapi.dto.PaymentDTO;
import com.linchi.payments.paymentsapi.dto.request.PaymentListReq;
import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.request.PaymentStatusReq;
import com.linchi.payments.paymentsapi.dto.response.PaymentListResp;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;
import com.linchi.payments.paymentsapi.entitys.Payment;
import com.linchi.payments.paymentsapi.entitys.PaymentIntent;
import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;
import com.linchi.payments.paymentsapi.excpetions.*;
import com.linchi.payments.paymentsapi.service.support.*;
import com.linchi.payments.paymentsapi.repository.PaymentRepository;
import com.linchi.payments.paymentsapi.service.managers.PaymentManagerService;
import com.linchi.payments.paymentsapi.service.payments.PaymentService;

import com.linchi.payments.paymentsapi.service.support.enums.ResultEnum;
import com.linchi.payments.paymentsapi.service.support.enums.CurrencyEnum;
import com.linchi.payments.paymentsapi.service.support.enums.ManagersEnum;
import com.linchi.payments.paymentsapi.service.support.factorys.ManagerFactory;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class PaymentServiceImpl implements PaymentService {

    /// TODO: considerar dejar solo los metodos que responden al endpoiont, y separar los metodos de apoyo en otro @Component

    private final PaymentRepository paymentRepository;
    private final ManagerFactory managerFactory;


    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, ManagerFactory managerFactory) {
        this.paymentRepository = paymentRepository;
        this.managerFactory = managerFactory;

    }

    /// Servicio para pagos y metodos de apoyo

    @Override
    public ResponseEntity<PaymentResp> doPayment(PaymentReq paymentReq, ManagersEnum manager) {

        // elegimos el manager de pago
        //si falla lanza excepcion, no sigue
        PaymentManagerService payManager = managerFactory.getPaymentMethod(manager);


        //Generamos el DTO de pago, cargamos entidad payment y paymentMethodX
        //si no se puede, no seguimos.
        PaymentDTO paymentDTO;
        try{
            paymentDTO = this.getPaymentDTO(paymentReq, manager);

            //la entidad payMethod, se ocupa el payManager porque conoce el tipo y puede castearlo
            paymentDTO.setMethod(
                    payManager.getMethod(paymentReq)
            );
        }catch (Exception e) {
            throw new DataConvertException(ResultEnum.DATA_CONVERT_ERROR, paymentReq);
        }

        //si ya existe la intencion de pago, cortamos
        if (
                paymentRepository
                        .findByPaymentIntent(paymentDTO.getPayment().getPaymentIntent())
                        .isPresent()
        ) {
            throw new DuplicatePayException(ResultEnum.PAYMENT_EXISTS);
        }

        //conversion de moneda
        paymentDTO.getPayment()
                .setLocalAmount(
                        paymentDTO.getPayment()
                                .getCurrency()
                                .rateToArs(
                                        paymentDTO.getPayment().getAmount()
                                )
                );

        //FIX_ME: no funciona como trasnaccion
        //pudimos generar las entidades, persisitmos como transaccion payment y method
        this.savePaymentAndMethod(paymentDTO, payManager);


        //enviamos al payManager para seguir su proceso
         try{
            payManager.processPayment(paymentDTO);

        } catch (Exception ignored) {

        }

        this.paymentRepository.save(
                paymentDTO.getPayment()
        );


         //TO_DO: mapper
        //esta repuesta solo sale si no se lanzo una excpecion desde otro lado
        //el RestControllerAdvice fucniona de interceptor
        //por ejemnplo para persisitr el resultado
        //respondemos
        ResponseEntity<PaymentResp> response;
        try{
            response = new ResponseEntity<>(
                    PaymentResp
                            .builder()
                            .result(paymentDTO.getResult())
                            .resultDescription(paymentDTO.getResult().getDescription())
                            .payment(paymentDTO.getPayment())
                            .build(),
                    HttpStatus.OK
            );
        }catch (Exception e) {
            throw new BusinessException (ResultEnum.VERIFY_STATUS);
        }

        return response;
    }



    @Transactional
    public void savePaymentAndMethod(PaymentDTO paymentDTO, PaymentManagerService payManager) {

        //se ocupa el servicio del metodo de pago, porque conoce el tipo y puede castearlo.
        payManager.saveTransaction(paymentDTO);

        this.paymentRepository
                .save(paymentDTO.getPayment());
    }


    private PaymentDTO getPaymentDTO(PaymentReq paymentReq, ManagersEnum method) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPayment(Mappers.mapPayReqToPayEntity(paymentReq));
        paymentDTO
                .getPayment()
                .setCreatedAt(
                        Timestamp.valueOf(LocalDateTime.now())
                );
        paymentDTO
                .getPayment()
                .setStatus(PaymentStatusEnum.STARTED);
        paymentDTO
                .getPayment()
                .setMethod(method);

        return paymentDTO;
    }


    ///  OTROS ENDPOINTS --------------------------------

    @Override
    public ResponseEntity<Payment> getPayment(PaymentStatusReq paymentStatusReq) {
        Payment payment =  paymentRepository.findByPaymentIntent(
                        PaymentIntent
                                .builder()
                                .payIntentionId(paymentStatusReq.getPayIntentionId())
                                .commerceId(paymentStatusReq.getCommerceId())
                                .build()
                )
                .orElseThrow( () -> new PaymentsNotFoundException(paymentStatusReq));


        return new ResponseEntity<>(payment, HttpStatus.OK);
    }



    @Override
    public List<String> getCurrency() {
        return Arrays.stream(CurrencyEnum.values())
                .map(currencyEnum -> currencyEnum.name() + ": " + currencyEnum.getRate())
                .collect(Collectors.toList());
    }

    public PaymentListResp getPaymentsList(PaymentListReq paymentListReq) {

        Pageable pageConfig = buildPageConfig(paymentListReq);
        Specification<Payment> specs = buildSpecs(paymentListReq);

        Page<Payment> page = paymentRepository.findAll(specs, pageConfig);

        return PaymentListResp.builder()
                .payments(page.getContent())
                .page(paymentListReq.getPage())
                .size(paymentListReq.getSize())
                .build();


    }

    ///  Metodos de apoyo para la busqueda dinamica
    // TODO: separar en clase aparte



    private Pageable buildPageConfig(PaymentListReq paymentListReq) {

        //config de paginado con sort
        if (paymentListReq.getSortBy() != null) {

            validateSort(paymentListReq);

            return PageRequest.of(
                    paymentListReq.getPage(),
                    paymentListReq.getSize(),
                    Sort.by(
                            paymentListReq.getSortDirection(),
                            paymentListReq.getSortBy()
                    )
            );
        }

        //config de paginado sin sort
        return PageRequest.of(
                paymentListReq.getPage(), paymentListReq.getSize()
        );
    }

    private void validateSort( PaymentListReq paymentListReq) {

        if (paymentListReq.getSortDirection() == null) {
            paymentListReq.setSortDirection(Sort.Direction.DESC);
        }

        List<String> validFields = Stream.of(Payment.class.getDeclaredFields())
                .map(Field::getName)
                .toList();

        if (!validFields.contains(paymentListReq.getSortBy())) {
            throw new InvalidFindFieldException(validFields.toString());
        }

    }


    private Specification<Payment> buildSpecs(PaymentListReq paymentListReq) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.equal(root.get("paymentIntent").get("commerceId"), paymentListReq.getCommerceId()));

            if (paymentListReq.getStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), paymentListReq.getStatus()));
            }

            if (paymentListReq.getMinAmount() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), paymentListReq.getMinAmount()));
            }

            if (paymentListReq.getMaxAmount() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("amount"), paymentListReq.getMaxAmount()));
            }

            if (paymentListReq.getMethod() != null) {
                predicates.add(criteriaBuilder.equal(root.get("method"), paymentListReq.getMethod()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
