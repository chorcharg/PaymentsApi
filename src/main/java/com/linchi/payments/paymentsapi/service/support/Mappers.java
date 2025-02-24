package com.linchi.payments.paymentsapi.service.support;

import com.linchi.payments.paymentsapi.dto.request.CardPaymentReq;
import com.linchi.payments.paymentsapi.dto.request.P2pPaymentReq;
import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.request.TransferPaymentReq;

import com.linchi.payments.paymentsapi.entitys.*;


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

    public static P2pPayment mapPayReqToP2pEntity(P2pPaymentReq paymentReq) {
        P2pPayment p2pPayment = new P2pPayment();
        p2pPayment.setSenderId(paymentReq.getSenderId());
        p2pPayment.setReceiverId(paymentReq.getReceiverId());
        p2pPayment.setNote(paymentReq.getNote());

        p2pPayment.setPaymentId(
                PaymentIntent
                        .builder()
                        .commerceId(paymentReq.getCommerceId())
                        .payIntentionId(paymentReq.getPayIntentionId())
                        .build()
        );

        return p2pPayment;
    }

    public static CardPayment mapPayReqToCardEntity(CardPaymentReq paymentReq) {
        CardPayment cardPayment = new CardPayment();
            cardPayment.setAuthorizer(paymentReq.getAuthorizer());
            cardPayment.setCardNumber(paymentReq.getCardNumber());
            cardPayment.setPaymentId(
                    PaymentIntent
                            .builder()
                            .commerceId(paymentReq.getCommerceId())
                            .payIntentionId(paymentReq.getPayIntentionId())
                            .build()
                    );

        return cardPayment;
    }

    public static TransferPayment mapPayReqToTransferEntity(TransferPaymentReq paymentReq) {
        TransferPayment transferPayment = new TransferPayment();
        transferPayment.setUserId(paymentReq.getUserId());
        transferPayment.setBankCode(paymentReq.getBankCode());
        transferPayment.setToAcct(paymentReq.getToAcct());
        transferPayment.setPaymentId(
                PaymentIntent
                        .builder()
                        .commerceId(paymentReq.getCommerceId())
                        .payIntentionId(paymentReq.getPayIntentionId())
                        .build()
        );


        return transferPayment;
    }


}

