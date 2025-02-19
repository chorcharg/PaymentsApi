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

    @Positive(message = "el valor no puede ser negativo")
    @NotNull(message = "comercio es obligatrorio")
    private int commerceId;

    @Positive(message = "el valor no puede ser negativo")
    @NotNull(message = "id de intencion de pago es obligatrorio")
    private int payIntentionId;

    @DecimalMin(value = "0.1", message = "el importe debe ser mayor a 0")
    @NotNull(message = "monto es obligatrorio")
    private Double amount;

    @NotNull(message = "tipo de moneda es obligatrorio")
    private CurrencyEnum currency;


}




