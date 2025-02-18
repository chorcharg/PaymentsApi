package com.linchi.payments.paymentsapi.controller;


import com.linchi.payments.paymentsapi.dto.request.CardPaymentReq;
import com.linchi.payments.paymentsapi.dto.request.P2pPaymentReq;
import com.linchi.payments.paymentsapi.dto.request.TransferPaymentReq;
import com.linchi.payments.paymentsapi.dto.response.PaymentListResp;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;
import com.linchi.payments.paymentsapi.dto.response.PaymentStatusResp;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Validated
@RequestMapping("api/payments")
public interface PaymentController {




    @PostMapping("card")
    public ResponseEntity<PaymentResp> cardPayment(@Valid @RequestBody CardPaymentReq cardPaymentReq);

    @PostMapping("p2p")
    public ResponseEntity<PaymentResp> p2pPayment(@Valid @RequestBody P2pPaymentReq p2pPaymentReq);

    @PostMapping("transfer")
    public ResponseEntity<PaymentResp> transferPayment(@Valid @RequestBody TransferPaymentReq transferPaymentReq);


    @PostMapping("list")
    public ResponseEntity<PaymentListResp> paymentsList();

    @PostMapping("status")
    public ResponseEntity<PaymentStatusResp> paymentStatus();

    @GetMapping("/health")
    public ResponseEntity<String> health();

}
