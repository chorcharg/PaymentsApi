package com.linchi.payments.paymentsapi.dto.request;

public class TransferPaymentReq extends PaymentReq {

    long userId;
    private String bankCode;
    private String toAcct;

}
