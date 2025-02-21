package com.linchi.payments.paymentsapi.service.managers;

import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;

import org.springframework.http.ResponseEntity;

//reglas de negocio por tipo de pago.
//elije proveedor y pide auotrizacion

public interface PaymentManagerService {

    public ResponseEntity<PaymentResp> processPayment(PaymentReq paymentReq);

    public void saveTransaction(PaymentReq paymentReq);



}
