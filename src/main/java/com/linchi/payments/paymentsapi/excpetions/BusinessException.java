package com.linchi.payments.paymentsapi.excpetions;

import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.service.support.enums.BussinesResultEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException{

    private final BussinesResultEnum exception;
    private final PaymentReq paymentReq;

    public BusinessException(BussinesResultEnum bussinesResultEnum , PaymentReq paymentReq) {
        super(bussinesResultEnum.getDescription());
        this.paymentReq = paymentReq;
        this.exception = bussinesResultEnum;
    }


}
