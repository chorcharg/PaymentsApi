package com.linchi.payments.paymentsapi.service.payments.impl;


import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;

import com.linchi.payments.paymentsapi.entitys.Payment;
import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;
import com.linchi.payments.paymentsapi.excpetions.BussinesException;
import com.linchi.payments.paymentsapi.excpetions.ExceptionEnum;
import com.linchi.payments.paymentsapi.repository.PaymentRepository;
import com.linchi.payments.paymentsapi.service.managers.PaymentManagerService;
import com.linchi.payments.paymentsapi.service.payments.PaymentService;
import com.linchi.payments.paymentsapi.service.support.ManagerFactory;
import com.linchi.payments.paymentsapi.service.support.Mappers;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.LocalDateTime;


@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    ManagerFactory managerFactory;

    @Override
    public ResponseEntity<PaymentResp> doPayment(PaymentReq paymentReq) {

        //generamos la entidad desde el request
        Payment payment = Mappers.mapPayReqToPayEntity(paymentReq);

        //verificamos que la intencion de pago no exista (idempotencia)
        payment = paymentRepository
                    .findByPaymentIntent(payment.getPaymentIntent())
                    .orElseThrow(() -> new BussinesException(ExceptionEnum.PAYMENT_EXISTS, paymentReq) );



        //vamos al factory para elegir el manager de pago
        PaymentManagerService payManager= managerFactory.getPaymentMethod(paymentReq);

        //ya tenemos el manager, persisitmos con transaction en DB
        payment.setStatus(PaymentStatusEnum.STARTED);
        payment.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        this.saveTransaction(payment, payManager, paymentReq);

        //procesamos
        ResponseEntity<PaymentResp>  httpPaymentResp = payManager.processPayment(paymentReq);

        //guardamos el resultado en la DB

        payment.setStatus(httpPaymentResp.getBody().getStatus());
        payment.setDescription(httpPaymentResp.getBody().getStatusDescription());
        paymentRepository.save(payment);

        return httpPaymentResp;
    }

    @Transactional
    public void saveTransaction(Payment payment, PaymentManagerService payManager, PaymentReq paymentReq) {
        this.paymentRepository.save(payment);
        payManager.saveTransaction(paymentReq);

    }

}
