package com.linchi.payments.paymentsapi.service.payments.impl;


import com.linchi.payments.paymentsapi.dto.request.PaymentListReq;
import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.request.PaymentStatusReq;

import com.linchi.payments.paymentsapi.dto.response.PaymentListResp;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;
import com.linchi.payments.paymentsapi.entitys.Payment;
import com.linchi.payments.paymentsapi.entitys.PaymentIntent;
import com.linchi.payments.paymentsapi.entitys.enums.CurrencyEnum;
import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;
import com.linchi.payments.paymentsapi.excpetions.BusinessException;
import com.linchi.payments.paymentsapi.excpetions.PaymentsNotFoundException;
import com.linchi.payments.paymentsapi.service.support.BussinesResultEnum;
import com.linchi.payments.paymentsapi.repository.PaymentRepository;
import com.linchi.payments.paymentsapi.service.managers.PaymentManagerService;
import com.linchi.payments.paymentsapi.service.payments.PaymentService;
import com.linchi.payments.paymentsapi.service.support.ManagerFactory;
import com.linchi.payments.paymentsapi.service.support.Mappers;

import com.linchi.payments.paymentsapi.service.support.MonExt;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    ManagerFactory managerFactory;

    @Override
    public ResponseEntity<PaymentResp> doPayment(PaymentReq paymentReq) {

        //vamos al factory para elegir el manager de pago
        PaymentManagerService payManager = managerFactory.getPaymentMethod(paymentReq);

        Payment payment = this.startPayment(paymentReq, payManager);
        PaymentResp paymentResp = this.callManager(payManager, paymentReq);

        this.finish(payment, paymentResp);

        return new ResponseEntity<PaymentResp>(paymentResp, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PaymentResp> getPayment(PaymentStatusReq paymentStatusReq) {
        Payment payment =  paymentRepository.findByPaymentIntent(
        PaymentIntent
                .builder()
                .payIntentionId(paymentStatusReq.getPayIntentionId())
                .commerceId(paymentStatusReq.getCommerceId())
                .build()
                )
                .orElseThrow( () -> new PaymentsNotFoundException(paymentStatusReq));

        PaymentReq paymentReq = Mappers.mapPayEntityToPaymentReq(payment);

        PaymentResp paymentResp = Mappers.mapPayReqToPayResp(
                paymentReq,
                payment.getStatus(),
                payment.getDescription()
        );

        return new ResponseEntity<>(paymentResp, HttpStatus.OK);
    }

    @Override
    public PaymentListResp getPaymentsList(PaymentListReq paymentListReq) {

        Pageable pageable = PageRequest.of(paymentListReq.getPage(), paymentListReq.getSize());
        Page<Payment> page = paymentRepository.findByPaymentIntent_CommerceId(paymentListReq.getCommerceId(), pageable);
        PaymentListResp paymentListResp =
                PaymentListResp
                        .builder()
                        .payments(page.getContent())
                        .page(paymentListReq.getPage())
                        .size(paymentListReq.getSize())
                        .build();

        return paymentListResp;
    }

    @Transactional
    public Payment startPayment(PaymentReq paymentReq, PaymentManagerService payManager) {

        Payment payment = Mappers.mapPayReqToPayEntity(paymentReq);
        MonExt monExt = MonExt.valueOf(payment.getCurrency().toString());
        payment.setLocalAmount(
                monExt.rateToArs(payment.getAmount())
        );

        if (paymentRepository.findByPaymentIntent(payment.getPaymentIntent()).isPresent()) {
            throw new BusinessException(BussinesResultEnum.PAYMENT_EXISTS, paymentReq);
        }

        paymentReq.setCurrency(CurrencyEnum.ARS);
        paymentReq.setAmount(payment.getLocalAmount());

        payment.setStatus(PaymentStatusEnum.STARTED);
        payment.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        this.saveTransaction(payment, payManager, paymentReq);
        return payment;
    }

    private PaymentResp callManager(PaymentManagerService payManager, PaymentReq paymentReq) {

        PaymentResp paymentResp = payManager.processPayment(paymentReq);


        return paymentResp;
    }

    private void finish(Payment payment, PaymentResp paymentResp) {
        payment.setStatus(paymentResp.getStatus());
        payment.setDescription(paymentResp.getStatusDescription());
        this.paymentRepository.save(payment);
    }

    @Transactional
    public void saveTransaction(Payment payment, PaymentManagerService payManager, PaymentReq paymentReq) {
        this.paymentRepository.save(payment);
        payManager.saveTransaction(paymentReq);

    }
}
