package com.linchi.payments.paymentsapi.dto.request;

import com.linchi.payments.paymentsapi.entitys.Payment;

public class CardPaymentReq extends PaymentReq {

    private Payment paymentId;
    private String cardNumber;

}
