package com.linchi.payments.paymentsapi.service.payments.impl;
import com.linchi.payments.paymentsapi.dto.PaymentDTO;
import com.linchi.payments.paymentsapi.dto.request.PaymentListReq;
import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.request.PaymentStatusReq;
import com.linchi.payments.paymentsapi.dto.response.PaymentListResp;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;
import com.linchi.payments.paymentsapi.entitys.Payment;
import com.linchi.payments.paymentsapi.entitys.PaymentIntent;
import com.linchi.payments.paymentsapi.excpetions.*;
import com.linchi.payments.paymentsapi.repository.PaymentRepository;
import com.linchi.payments.paymentsapi.service.managers.PaymentManagerService;
import com.linchi.payments.paymentsapi.service.payments.PaymentService;
import com.linchi.payments.paymentsapi.service.support.enums.CurrencyEnum;
import com.linchi.payments.paymentsapi.service.support.enums.InternalResultEnum;
import com.linchi.payments.paymentsapi.service.support.enums.ManagersEnum;
import com.linchi.payments.paymentsapi.service.support.factorys.ManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentSupportImpl paymentSupport;
    private final PaymentRepository paymentRepository;
    private final ManagerFactory managerFactory;


    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, ManagerFactory managerFactory, PaymentSupportImpl paymentSupport) {
        this.paymentRepository = paymentRepository;
        this.managerFactory = managerFactory;
        this.paymentSupport = paymentSupport;

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
            paymentDTO = this.paymentSupport.getPaymentDTO(paymentReq, manager);

            //la entidad payMethod, se ocupa el payManager porque conoce el tipo y puede castearlo
            paymentDTO.setMethod(
                    payManager.getMethod(paymentReq)
            );
        }catch (Exception e) {
            throw new InternalException(InternalResultEnum.DATA_CONVERT_ERROR);
        }


        //si ya existe la intencion de pago, cortamos
        if (
                paymentRepository
                        .findOneByPaymentIntent(paymentDTO.getPayment().getPaymentIntent())
                        .isPresent()
        ) {
            throw new InternalException(InternalResultEnum.PAYMENT_EXISTS);
        }

        //pudimos generar las entidades, persisitmos como transaccion payment y method

        this.paymentSupport.startPayment(paymentDTO, payManager);

        //enviamos al payManager para seguir su proceso
         try{
            payManager.processPayment(paymentDTO);

        } catch (Exception ignored) {

        }

        this.paymentRepository.save(
                paymentDTO.getPayment()
        );


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
            throw new InternalException (InternalResultEnum.VERIFY_STATUS);
        }

        return response;
    }


    ///  OTROS ENDPOINTS --------------------------------

    @Override
    public ResponseEntity<Payment> getPayment(PaymentStatusReq paymentStatusReq) {
        Payment payment =  paymentRepository.findOneByPaymentIntent(
                        PaymentIntent
                                .builder()
                                .payIntentionId(paymentStatusReq.getPayIntentionId())
                                .commerceId(paymentStatusReq.getCommerceId())
                                .build()
                )
                .orElseThrow( () -> new InternalException(InternalResultEnum.PAYMENT_NOT_FOUND));


        return new ResponseEntity<>(payment, HttpStatus.OK);
    }


    @Override
    public List<String> getCurrency() {
        return Arrays.stream(CurrencyEnum.values())
                .map(currencyEnum -> currencyEnum.name() + ": " + currencyEnum.getRate())
                .collect(Collectors.toList());
    }

    public PaymentListResp getPaymentsList(PaymentListReq paymentListReq) {

        Pageable pageConfig = this.paymentSupport.buildPageConfig(paymentListReq);
        Specification<Payment> specs = this.paymentSupport.buildSpecs(paymentListReq);

        Page<Payment> page = paymentRepository.findAll(specs, pageConfig);

        return PaymentListResp.builder()
                .payments(page.getContent())
                .page(paymentListReq.getPage())
                .size(paymentListReq.getSize())
                .build();


    }

}
