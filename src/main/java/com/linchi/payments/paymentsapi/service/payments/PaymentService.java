package com.linchi.payments.paymentsapi.service.payments;

import com.linchi.payments.paymentsapi.dto.request.PaymentListReq;
import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.request.PaymentStatusReq;
import com.linchi.payments.paymentsapi.dto.response.PaymentListResp;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;

import com.linchi.payments.paymentsapi.entitys.Payment;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

// reglas de negocio de la api
// se ocupa de la persistencia y actualizacion de estados

public interface PaymentService {

    public ResponseEntity<PaymentResp> doPayment(PaymentReq paymentReq);

    public ResponseEntity<PaymentResp> getPayment(PaymentStatusReq paymentStatusReq);

    public PaymentListResp getPaymentsList(PaymentListReq paymentListReq);

    public List<String> getCurrency();
}
