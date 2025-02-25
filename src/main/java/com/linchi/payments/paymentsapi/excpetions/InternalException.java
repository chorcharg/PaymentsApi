package com.linchi.payments.paymentsapi.excpetions;


import com.linchi.payments.paymentsapi.service.support.enums.InternalResultEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class InternalException extends RuntimeException {

    private final InternalResultEnum result;

    public InternalException(InternalResultEnum result) {

        this.result = result;
    }

}
