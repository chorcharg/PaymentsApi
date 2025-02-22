package com.linchi.payments.paymentsapi.service.support;

import com.linchi.payments.paymentsapi.dto.request.CardPaymentReq;
import com.linchi.payments.paymentsapi.dto.request.P2pPaymentReq;
import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.request.TransferPaymentReq;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;
import com.linchi.payments.paymentsapi.entitys.*;
import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;

public final class Mappers {
    public static Payment mapPayReqToPayEntity(PaymentReq paymentReq) {

        Payment payment = new Payment();
        payment.setPaymentIntent(new PaymentIntent());
        payment.getPaymentIntent().setPayIntentionId(paymentReq.getPayIntentionId());
        payment.getPaymentIntent().setCommerceId(paymentReq.getCommerceId());
        payment.setCurrency(paymentReq.getCurrency());
        payment.setAmount(paymentReq.getAmount());

        return payment;
    }

    public static PaymentResp mapPayReqToPayResp(PaymentReq paymentReq, PaymentStatusEnum status, String statusDescription) {

        return  PaymentResp.builder()
                .paymentReq(paymentReq)
                .status(status)
                .statusDescription(statusDescription)
                .build();
    }

    public static CardPayment mapCardPayReqToCardEntity(PaymentReq paymentReq) {
        CardPaymentReq cardPaymentReq = (CardPaymentReq) paymentReq;
        return CardPayment
                .builder()
                .paymentId(PaymentIntent
                            .builder()
                            .commerceId(paymentReq.getCommerceId())
                            .payIntentionId(paymentReq.getPayIntentionId())
                            .build())
                .cardNumber(cardPaymentReq.getCardNumber())
                .authorizer(cardPaymentReq.getAuthorizer())
                .build();
    }

    public static P2pPayment mapP2pPayReqToP2pEntity(PaymentReq paymentReq) {
        P2pPaymentReq p2pPaymentReq = (P2pPaymentReq) paymentReq;

        return P2pPayment
                .builder()
                .paymentId(PaymentIntent
                        .builder()
                        .commerceId(paymentReq.getCommerceId())
                        .payIntentionId(paymentReq.getPayIntentionId())
                        .build())
                .senderId(p2pPaymentReq.getSenderId())
                .receiverId(p2pPaymentReq.getReceiverId())
                .note(p2pPaymentReq.getNote())

                .build();

    }

    public static TransferPayment mapTransfPayReqToTransfEntity(PaymentReq paymentReq) {
        TransferPaymentReq transferPaymentReq = (TransferPaymentReq) paymentReq;

        return TransferPayment
                .builder()
                .paymentId(PaymentIntent
                        .builder()
                        .commerceId(paymentReq.getCommerceId())
                        .payIntentionId(paymentReq.getPayIntentionId())
                        .build())
                .userId(transferPaymentReq.getUserId())
                .bankCode(transferPaymentReq.getBankCode())
                .toAcct(transferPaymentReq.getToAcct())
                .build();


    }

    public static PaymentReq mapPayEntityToPaymentReq(Payment payment) {
        return PaymentReq
                .builder()
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .commerceId(payment.getPaymentIntent().getCommerceId())
                .payIntentionId(payment.getPaymentIntent().getPayIntentionId())
                .build();

    }

}

