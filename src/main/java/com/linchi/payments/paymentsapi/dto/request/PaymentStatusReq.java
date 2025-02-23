package com.linchi.payments.paymentsapi.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentStatusReq {


    @Positive
    @NotNull
    private int commerceId;

    @Positive
    @NotNull
    private int payIntentionId;

}
