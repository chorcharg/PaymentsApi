package com.linchi.payments.paymentsapi.controller.impl;

import com.linchi.payments.paymentsapi.controller.PaymentController;
import com.linchi.payments.paymentsapi.dto.request.CardPaymentReq;
import com.linchi.payments.paymentsapi.dto.request.P2pPaymentReq;
import com.linchi.payments.paymentsapi.dto.request.TransferPaymentReq;
import com.linchi.payments.paymentsapi.dto.response.PaymentListResp;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;
import com.linchi.payments.paymentsapi.dto.response.PaymentStatusResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class paymentsControllerImpl implements PaymentController {



    @Override
    public  ResponseEntity<PaymentResp> cardPayment(CardPaymentReq cardPaymentReq) {

        return null;
    }

    @Override
    public ResponseEntity<PaymentResp> p2pPayment(P2pPaymentReq p2pPaymentReq) {

        return null;
    }

    @Override
    public ResponseEntity<PaymentResp> transferPayment(TransferPaymentReq transferPaymentReq) {

        return null;
    }

    @Override
    public ResponseEntity<PaymentListResp> paymentsList() {
        return null;
    }

    @Override
    public ResponseEntity<PaymentStatusResp> paymentStatus() {
        return null;
    }
}
