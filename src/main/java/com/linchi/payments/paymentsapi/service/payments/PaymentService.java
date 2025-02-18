package com.linchi.payments.paymentsapi.service.payments;

import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;
import org.springframework.http.ResponseEntity;


public interface PaymentService {

    public ResponseEntity<PaymentResp> doPayment(PaymentReq paymentReq);

}
