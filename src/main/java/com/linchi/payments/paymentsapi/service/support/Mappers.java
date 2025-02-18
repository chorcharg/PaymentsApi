package com.linchi.payments.paymentsapi.service.support;

import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;
import com.linchi.payments.paymentsapi.entitys.Payment;
import com.linchi.payments.paymentsapi.entitys.PaymentIntent;
import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;

import java.util.Locale;

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
                .amount(paymentReq.getAmount())
                .currency(paymentReq.getCurrency())
                .commerceId(paymentReq.getCommerceId())
                .payIntentionId(paymentReq.getPayIntentionId())
                .status(status)
                .statusDescription(statusDescription)
                .build();

    }

}

