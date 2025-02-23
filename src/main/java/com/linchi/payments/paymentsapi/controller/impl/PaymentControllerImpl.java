package com.linchi.payments.paymentsapi.controller.impl;

import com.linchi.payments.paymentsapi.controller.PaymentController;
import com.linchi.payments.paymentsapi.dto.request.*;
import com.linchi.payments.paymentsapi.dto.response.PaymentListResp;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;

import com.linchi.payments.paymentsapi.entitys.Payment;
import com.linchi.payments.paymentsapi.service.payments.PaymentService;

import com.linchi.payments.paymentsapi.service.support.enums.ManagersEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class PaymentControllerImpl implements PaymentController {


    private final PaymentService paymentService;

    @Autowired
    public PaymentControllerImpl(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public  ResponseEntity<PaymentResp> cardPayment(CardPaymentReq cardPaymentReq) {
        return this.paymentService.doPayment(cardPaymentReq, ManagersEnum.DEBIT_CARD);
    }

    @Override
    public ResponseEntity<PaymentResp> p2pPayment(P2pPaymentReq p2pPaymentReq) {
        return this.paymentService.doPayment(p2pPaymentReq, ManagersEnum.P2P);
    }

    @Override
    public ResponseEntity<PaymentResp> transferPayment(TransferPaymentReq transferPaymentReq) {
        return this.paymentService.doPayment(transferPaymentReq, ManagersEnum.TRANSFER);
    }


    @Override
    public PaymentListResp paymentsList(PaymentListReq paymentListReq) {

        return paymentService.getPaymentsList(paymentListReq);
    }

    @Override
    public ResponseEntity<Payment> paymentStatus(PaymentStatusReq paymentStatusReq) {
        return paymentService.getPayment(paymentStatusReq);
    }

    @Override
    public ResponseEntity<String> health(){
        return new ResponseEntity<>(
                "OK",
                HttpStatus.OK
        );
    }

    @Override
    public List<String> currency() {
        return paymentService.getCurrency();
    }

}
