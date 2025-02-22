package com.linchi.payments.paymentsapi.controller;

import com.linchi.payments.paymentsapi.dto.request.*;
import com.linchi.payments.paymentsapi.dto.response.PaymentListResp;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;
import com.linchi.payments.paymentsapi.dto.response.PaymentStatusResp;

import com.linchi.payments.paymentsapi.entitys.Payment;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Validated
@RequestMapping("api/payments")
public interface PaymentController {

    @PostMapping("/card")
    public ResponseEntity<PaymentResp> cardPayment(@Valid @RequestBody CardPaymentReq cardPaymentReq);

    @PostMapping("/p2p")
    public ResponseEntity<PaymentResp> p2pPayment(@Valid @RequestBody P2pPaymentReq p2pPaymentReq);

    @PostMapping("/transfer")
    public ResponseEntity<PaymentResp> transferPayment(@Valid @RequestBody TransferPaymentReq transferPaymentReq);

    @PostMapping("/list")
    public PaymentListResp paymentsList(@Valid @RequestBody PaymentListReq paymentListReq);

    @PostMapping("/status")
    public ResponseEntity<PaymentResp> paymentStatus(@Valid @RequestBody PaymentStatusReq paymentStatusReq);

    @GetMapping("/health")
    public ResponseEntity<String> health();

}
