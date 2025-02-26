package com.linchi.payments.paymentsapi.service.payments;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.linchi.payments.paymentsapi.dto.request.PaymentListReq;
import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.request.PaymentStatusReq;
import com.linchi.payments.paymentsapi.dto.response.PaymentListResp;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;
import com.linchi.payments.paymentsapi.entitys.Payment;
import com.linchi.payments.paymentsapi.service.support.enums.ManagersEnum;

// reglas de negocio de la api
// se ocupa de la persistencia y actualizacion de estados

public interface PaymentService {

    ResponseEntity<PaymentResp> doPayment(PaymentReq paymentReq, ManagersEnum manager);

    ResponseEntity<Payment> getPayment(PaymentStatusReq paymentStatusReq);

    PaymentListResp getPaymentsList(PaymentListReq paymentListReq);

    List<String> getCurrency();
}
