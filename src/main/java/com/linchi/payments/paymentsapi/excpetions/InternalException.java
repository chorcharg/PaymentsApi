package com.linchi.payments.paymentsapi.excpetions;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.linchi.payments.paymentsapi.service.support.enums.InternalResultEnum;

@EqualsAndHashCode(callSuper = true)
@Data
public class InternalException extends RuntimeException {

    private final InternalResultEnum result;

    public InternalException(InternalResultEnum result) {
        this.result = result;
    }

}
