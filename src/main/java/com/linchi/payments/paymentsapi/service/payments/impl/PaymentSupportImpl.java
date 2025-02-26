package com.linchi.payments.paymentsapi.service.payments.impl;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.linchi.payments.paymentsapi.dto.PaymentDTO;
import com.linchi.payments.paymentsapi.dto.request.PaymentListReq;
import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.entitys.Payment;
import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;
import com.linchi.payments.paymentsapi.excpetions.InvalidFindFieldException;
import com.linchi.payments.paymentsapi.repository.PaymentRepository;
import com.linchi.payments.paymentsapi.service.managers.PaymentManagerService;
import com.linchi.payments.paymentsapi.service.payments.PaymentSupport;
import com.linchi.payments.paymentsapi.service.support.Mappers;
import com.linchi.payments.paymentsapi.service.support.enums.ManagersEnum;
import com.linchi.payments.paymentsapi.service.support.enums.BusinessResultEnum;

@Service
public class PaymentSupportImpl implements PaymentSupport {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentSupportImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public void startPayment(PaymentDTO paymentDTO, PaymentManagerService payManager) {
        //se ocupa el servicio del metodo de pago, porque conoce el tipo y puede castearlo.
        payManager.saveTransaction(paymentDTO);
        this.paymentRepository
                .save(paymentDTO.getPayment());
    }

    public void updatePaymentDTO(PaymentDTO paymentDTO, BusinessResultEnum result) {
        paymentDTO
                .getPayment()
                .setStatus(result.getStatus());
        paymentDTO
                .setResult(result);
        paymentDTO
                .getPayment()
                .setDescription(result.getDescription());
    }

    public void updatePayment(PaymentDTO paymentDTO) {
        this.paymentRepository.save(paymentDTO.getPayment());
    }

    public PaymentDTO getPaymentDTO(PaymentReq paymentReq, ManagersEnum method) {
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
        paymentDTO
                .getPayment()
                .setLocalAmount(
                       paymentReq
                               .getCurrency()
                                .rateToArs(paymentReq.getAmount())
                );

        return paymentDTO;
    }

    ///busqueda

    public Pageable buildPageConfig(PaymentListReq paymentListReq) {


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

    public Specification<Payment> buildSpecs(PaymentListReq paymentListReq) {
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
