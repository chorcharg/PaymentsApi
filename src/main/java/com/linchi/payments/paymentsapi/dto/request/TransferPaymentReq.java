package com.linchi.payments.paymentsapi.dto.request;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor

public class TransferPaymentReq extends PaymentReq {

    long userId;
    private String bankCode;
    private String toAcct;

}
