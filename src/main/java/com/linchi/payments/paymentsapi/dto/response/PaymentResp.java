package com.linchi.payments.paymentsapi.dto.response;

import com.linchi.payments.paymentsapi.entitys.enums.CurrencyEnum;
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
    private int commerceId;
    private int payIntentionId;
    private Double amount;
    private CurrencyEnum currency;
    private PaymentStatusEnum status;
    private String statusDescription;


}
