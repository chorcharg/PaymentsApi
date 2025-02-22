package com.linchi.payments.paymentsapi.dto.request;


import com.linchi.payments.paymentsapi.entitys.enums.CurrencyEnum;
import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor

public class PaymentListReq {
    private int commerceId;
    private int size;
    private int page;

}
