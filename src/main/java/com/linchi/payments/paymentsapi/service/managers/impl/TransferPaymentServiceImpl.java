package com.linchi.payments.paymentsapi.service.managers.impl;

import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;
import com.linchi.payments.paymentsapi.service.managers.PaymentManagerService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TransferPaymentServiceImpl implements PaymentManagerService {

    @Override
    public PaymentResp processPayment(PaymentReq paymentReq) { return null; }

    @Override
    public void saveTransaction(PaymentReq paymentReq) {}
}
