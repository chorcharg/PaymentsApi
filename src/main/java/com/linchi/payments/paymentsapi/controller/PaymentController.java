package com.linchi.payments.paymentsapi.controller;


import com.linchi.payments.paymentsapi.dto.request.CardPaymentReq;
import com.linchi.payments.paymentsapi.dto.request.P2pPaymentReq;
import com.linchi.payments.paymentsapi.dto.request.TransferPaymentReq;
import com.linchi.payments.paymentsapi.dto.response.PaymentListResp;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;
import com.linchi.payments.paymentsapi.dto.response.PaymentStatusResp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("api/payments")
public interface PaymentController {

    @PostMapping("card")
    public ResponseEntity<PaymentResp> cardPayment(@RequestBody CardPaymentReq cardPaymentReq);

    @PostMapping("p2p")
    public ResponseEntity<PaymentResp> p2pPayment(@RequestBody P2pPaymentReq p2pPaymentReq);

    @PostMapping("transfer")
    public ResponseEntity<PaymentResp> transferPayment(@RequestBody TransferPaymentReq transferPaymentReq);

    @PostMapping("list")
    public ResponseEntity<PaymentListResp> paymentsList();

    @PostMapping("status")
    public ResponseEntity<PaymentStatusResp> paymentStatus();

}
