package com.linchi.payments.paymentsapi.excpetions;


import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.service.support.enums.ResultEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DataConvertException extends RuntimeException {

    private final ResultEnum exception;
    private final PaymentReq paymentReq;

    public DataConvertException(ResultEnum resultEnum, PaymentReq paymentReq) {
        super(resultEnum.getDescription());
        this.paymentReq = paymentReq;
        this.exception = resultEnum;
    }
}
