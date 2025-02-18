package com.linchi.payments.paymentsapi.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.linchi.payments.paymentsapi.entitys.enums.CurrencyEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentReq {

    private int commerceId;
    private int payIntentionId;
    private Double amount;
    private CurrencyEnum currency;


}




