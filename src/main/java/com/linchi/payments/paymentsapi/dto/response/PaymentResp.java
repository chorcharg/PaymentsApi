package com.linchi.payments.paymentsapi.dto.response;

import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResp {

    private PaymentStatusEnum status;
    private String statusDescription;
    private PaymentReq paymentReq;

}
