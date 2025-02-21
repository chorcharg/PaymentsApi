package com.linchi.payments.paymentsapi.excpetions;

import com.linchi.payments.paymentsapi.dto.request.PaymentReq;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FactoryException extends RuntimeException{

    private final String className;
    private final PaymentReq paymentReq;

    public FactoryException( String className, PaymentReq paymentReq) {

        this.className = className;
        this.paymentReq = paymentReq;
    }
}
