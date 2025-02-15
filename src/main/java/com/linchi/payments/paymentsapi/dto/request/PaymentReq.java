package com.linchi.payments.paymentsapi.dto.request;

import lombok.Data;

@Data
public class PaymentReq {

    private int commerceId;
    private String payIntentionId;
    private Double amount;
    private String currency;


}
