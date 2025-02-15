package com.linchi.payments.paymentsapi.dto.response;

import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;

public class PaymentResp {
    private int commerceId;
    private String payIntentionId;
    private Double amount;
    private String currency;
    private PaymentStatusEnum status;


}
