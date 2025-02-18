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

    @Positive(message = "el valor no puede ser negativo")
    @NotNull(message = "pagador es obligatorio")
    long userId;

    @NotBlank(message = "banco destino es obligatorio")
    private String bankCode;

    @NotBlank(message = "cuenta destino obligatorio")
    private String toAcct;

}
