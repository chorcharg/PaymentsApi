package com.linchi.payments.paymentsapi.service.payments.impl;


import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;

import com.linchi.payments.paymentsapi.entitys.Payment;
import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;
import com.linchi.payments.paymentsapi.repository.PaymentRepository;
import com.linchi.payments.paymentsapi.service.managers.PaymentManagerService;
import com.linchi.payments.paymentsapi.service.payments.PaymentService;
import com.linchi.payments.paymentsapi.service.support.ManagerFactory;
import com.linchi.payments.paymentsapi.service.support.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

        //generamos la entidad para la DB
        Payment payment = Mappers.mapPayReqToPayEntity(paymentReq);


        //verificamos que la intencion de pago no exista (idempotencia)
        if( paymentRepository
                .findByPaymentIntent(payment.getPaymentIntent())!=null)
        {       return new ResponseEntity<PaymentResp>(
                    Mappers.mapPayReqToPayResp(
                            paymentReq,
                            PaymentStatusEnum.ERROR,
                            "ID Intencion de pago ya existente"),
                    HttpStatus.BAD_REQUEST);
        };

        //si no existe, seteamos estatus, hora, y guardamos
        payment.setStatus(PaymentStatusEnum.STARTED);
        payment.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        paymentRepository.save(payment);

        //vamos al factory para elegir el manager de pago y pedimos el procesamos
        PaymentManagerService payManager= managerFactory.getPaymentMethod(paymentReq);
        ResponseEntity<PaymentResp>  paymentResp = payManager.processPayment(paymentReq);

        //guardamos el resultado del servicio en la DB

        payment.setStatus(payment.getStatus());
        payment.setDescription(paymentResp.getBody().getStatusDescription());
        paymentRepository.save(payment);

        return paymentResp;
    }



}
