package com.linchi.payments.paymentsapi.dto;


import com.linchi.payments.paymentsapi.entitys.MethodBase;
import com.linchi.payments.paymentsapi.entitys.Payment;
import com.linchi.payments.paymentsapi.service.support.enums.BusinessResultEnum;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class PaymentDTO {

    private Payment payment;
    private MethodBase method;
    private BusinessResultEnum result;


}
