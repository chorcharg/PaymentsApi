package com.linchi.payments.paymentsapi.excpetions;

import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = true)
@Data
public class BussinesException extends RuntimeException{

    private final ExceptionEnum exception;
    private final PaymentReq paymentReq;

    public BussinesException(ExceptionEnum excepcionEnum , PaymentReq paymentReq) {
        super(excepcionEnum.getDescription());
        this.paymentReq = paymentReq;
        this.exception = excepcionEnum;
    }


}
