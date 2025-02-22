package com.linchi.payments.paymentsapi.controller.impl;

import com.linchi.payments.paymentsapi.controller.PaymentController;
import com.linchi.payments.paymentsapi.dto.request.*;
import com.linchi.payments.paymentsapi.dto.response.PaymentListResp;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;

import com.linchi.payments.paymentsapi.service.payments.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PaymentControllerImpl implements PaymentController {

    @Autowired
    PaymentService paymentService;

    @Override
    public  ResponseEntity<PaymentResp> cardPayment(CardPaymentReq cardPaymentReq) {
        return this.paymentService.doPayment(cardPaymentReq);
    }

    @Override
    public ResponseEntity<PaymentResp> p2pPayment(P2pPaymentReq p2pPaymentReq) {
        return this.paymentService.doPayment(p2pPaymentReq);
    }

    @Override
    public ResponseEntity<PaymentResp> transferPayment(TransferPaymentReq transferPaymentReq) {
        return this.paymentService.doPayment(transferPaymentReq);
    }


    @Override
    public PaymentListResp paymentsList(PaymentListReq paymentListReq) {
        PaymentListResp payments = paymentService.getPaymentsList(paymentListReq);
        return payments;
    }

    @Override
    public ResponseEntity<PaymentResp> paymentStatus(PaymentStatusReq paymentStatusReq) {
        return paymentService.getPayment(paymentStatusReq);
    }

    @Override
    public ResponseEntity<String> health(){
        return new ResponseEntity<String>(
                "OK",
                HttpStatus.OK
        );
    }

}
