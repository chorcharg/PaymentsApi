package com.linchi.payments.paymentsapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferPaymentReq extends PaymentReq {

    @Positive
    @NotNull
    long userId;

    @NotBlank
    private String bankCode;

    @NotBlank
    private String toAcct;

}
