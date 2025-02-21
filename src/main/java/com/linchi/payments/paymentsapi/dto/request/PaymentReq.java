package com.linchi.payments.paymentsapi.dto.request;

import com.linchi.payments.paymentsapi.entitys.enums.CurrencyEnum;

import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentReq {

    @Positive
    @NotNull
    private int commerceId;

    @Positive
    @NotNull
    private int payIntentionId;

    @Positive
    @NotNull
    private Double amount;

    @NotNull
    private CurrencyEnum currency;


}




