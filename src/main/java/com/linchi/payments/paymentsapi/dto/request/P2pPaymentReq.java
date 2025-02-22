package com.linchi.payments.paymentsapi.dto.request;

import jakarta.validation.constraints.Digits;
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

    @Positive
    @NotNull
    private long senderId;

    @Positive
    @NotNull
    private long receiverId;

    private String note;

}
