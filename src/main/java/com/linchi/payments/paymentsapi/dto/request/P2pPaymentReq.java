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

public class P2pPaymentReq extends PaymentReq {

    @Positive(message = "el valor no puede ser negativo")
    @NotNull(message = "pagador es obligatorio")
    private long senderId;

    @Positive(message = "el valor no puede ser negativo")
    @NotNull(message = "comprador es obligatrorio")
    private long receiverId;

    private String note;

}
