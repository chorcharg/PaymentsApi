package com.linchi.payments.paymentsapi.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CardPaymentReq extends PaymentReq {


    @NotBlank(message = "Autorizador es obligatrorio")
    private String authorizer;


    @NotBlank(message = "tarjeta es obligatrorio")
    private String cardNumber;


}
