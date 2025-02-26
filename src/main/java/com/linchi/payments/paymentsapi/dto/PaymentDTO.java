package com.linchi.payments.paymentsapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.linchi.payments.paymentsapi.entitys.MethodBase;
import com.linchi.payments.paymentsapi.entitys.Payment;
import com.linchi.payments.paymentsapi.service.support.enums.BusinessResultEnum;

@Data
@NoArgsConstructor
public class PaymentDTO {

    private Payment payment;
    private MethodBase method;
    private BusinessResultEnum result;


}
