package com.linchi.payments.paymentsapi.service.Authorizers.impl;

import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;
import com.linchi.payments.paymentsapi.service.Authorizers.PaymentAuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserIdPaymentAuthServiceImpl implements PaymentAuthService {

    @Override
    public ResponseEntity<PaymentResp> doPayment(PaymentReq paymentReq) {
        return null;
    }

}
