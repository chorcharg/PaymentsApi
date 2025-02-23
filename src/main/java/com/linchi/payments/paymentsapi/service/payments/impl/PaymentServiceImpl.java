package com.linchi.payments.paymentsapi.service.payments.impl;
import com.linchi.payments.paymentsapi.dto.request.PaymentListReq;
import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.request.PaymentStatusReq;
import com.linchi.payments.paymentsapi.dto.response.PaymentListResp;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;
import com.linchi.payments.paymentsapi.entitys.Payment;
import com.linchi.payments.paymentsapi.entitys.PaymentIntent;
import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;
import com.linchi.payments.paymentsapi.excpetions.BusinessException;
import com.linchi.payments.paymentsapi.excpetions.PaymentsNotFoundException;
import com.linchi.payments.paymentsapi.service.support.*;
import com.linchi.payments.paymentsapi.repository.PaymentRepository;
import com.linchi.payments.paymentsapi.service.managers.PaymentManagerService;
import com.linchi.payments.paymentsapi.service.payments.PaymentService;

import com.linchi.payments.paymentsapi.service.support.enums.BussinesResultEnum;
import com.linchi.payments.paymentsapi.service.support.enums.CurrencyEnum;
import com.linchi.payments.paymentsapi.service.support.enums.ManagersEnum;
import com.linchi.payments.paymentsapi.service.support.factorys.ManagerFactory;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PaymentServiceImpl implements PaymentService {


    private final PaymentRepository paymentRepository;
    private final ManagerFactory managerFactory;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, ManagerFactory managerFactory) {
        this.paymentRepository = paymentRepository;
        this.managerFactory = managerFactory;
    }


    @Override
    public ResponseEntity<PaymentResp> doPayment(PaymentReq paymentReq, ManagersEnum manager) {

        //vamos al factory para elegir el manager de pago
        PaymentManagerService payManager = managerFactory.getPaymentMethod(manager);

        Payment payment = this.startPayment(paymentReq, payManager);

        PaymentResp paymentResp = this.callManager(payManager, paymentReq);

        this.finish(payment, paymentResp);

        return new ResponseEntity<PaymentResp>(paymentResp, HttpStatus.OK);
    }


    @Transactional
    public Payment startPayment(PaymentReq paymentReq, PaymentManagerService payManager) {

        Payment payment = Mappers.mapPayReqToPayEntity(paymentReq);

        payment.setLocalAmount(
                payment
                        .getCurrency()
                        .rateToArs(payment.getAmount())
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

    @Override
    public List<String> getCurrency() {
        return Arrays.stream(CurrencyEnum.values())
                .map(currencyEnum -> currencyEnum.name() + ": " + currencyEnum.getRate())
                .collect(Collectors.toList());
    }
}
