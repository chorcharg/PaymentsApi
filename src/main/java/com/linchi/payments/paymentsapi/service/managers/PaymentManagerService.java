package com.linchi.payments.paymentsapi.service.managers;

import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;
import com.linchi.payments.paymentsapi.service.support.Mappers;
import org.springframework.http.ResponseEntity;

public interface PaymentManagerService {


    public ResponseEntity<PaymentResp> processPayment(PaymentReq paymentReq);

    public void saveTransaction(PaymentReq paymentReq);



}
