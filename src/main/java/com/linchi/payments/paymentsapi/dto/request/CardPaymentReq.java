package com.linchi.payments.paymentsapi.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.linchi.payments.paymentsapi.entitys.Payment;
import lombok.*;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CardPaymentReq extends PaymentReq {

    @NotBlank(message = "Se debe informar la red de la tarjeta")
    @Positive(message = "El monto debe ser positivo")
    private String authorizer;

    private String cardNumber;


}
