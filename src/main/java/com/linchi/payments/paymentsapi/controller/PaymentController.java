package com.linchi.payments.paymentsapi.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.linchi.payments.paymentsapi.dto.request.CardPaymentReq;
import com.linchi.payments.paymentsapi.dto.request.P2pPaymentReq;
import com.linchi.payments.paymentsapi.dto.request.PaymentListReq;
import com.linchi.payments.paymentsapi.dto.request.TransferPaymentReq;
import com.linchi.payments.paymentsapi.dto.request.PaymentStatusReq;
import com.linchi.payments.paymentsapi.dto.response.PaymentListResp;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;
import com.linchi.payments.paymentsapi.entitys.Payment;


@Validated
@RequestMapping("api/payments")
public interface PaymentController {

    @PostMapping("/card")
    ResponseEntity<PaymentResp> cardPayment(@Valid @RequestBody CardPaymentReq cardPaymentReq);

    @PostMapping("/p2p")
    ResponseEntity<PaymentResp> p2pPayment(@Valid @RequestBody P2pPaymentReq p2pPaymentReq);

    @PostMapping("/transfer")
    ResponseEntity<PaymentResp> transferPayment(@Valid @RequestBody TransferPaymentReq transferPaymentReq);

    @PostMapping("/list")
    PaymentListResp paymentsList(@Valid @RequestBody PaymentListReq paymentListReq);

    @PostMapping("/status")
    ResponseEntity<Payment> paymentStatus(@Valid @RequestBody PaymentStatusReq paymentStatusReq);

    @GetMapping("/health")
    ResponseEntity<String> health();

    @GetMapping("/currency")
    List<String> currency();

}
