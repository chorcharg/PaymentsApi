package com.linchi.payments.paymentsapi.dto.request;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardPaymentReq extends PaymentReq {

    @NotBlank
    private String authorizer;

    @NotBlank
    private String cardNumber;

}
