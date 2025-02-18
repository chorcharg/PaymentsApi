package com.linchi.payments.paymentsapi.dto.request;


import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor

public class P2pPaymentReq extends PaymentReq {

    private long senderId;
    private long receiverId;
    private String note;

}
