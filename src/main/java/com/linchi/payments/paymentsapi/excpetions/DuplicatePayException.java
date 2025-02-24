package com.linchi.payments.paymentsapi.excpetions;


import com.linchi.payments.paymentsapi.service.support.enums.ResultEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DuplicatePayException extends RuntimeException {

    private final ResultEnum result;

    public DuplicatePayException(ResultEnum result) {

        this.result = result;
    }

}
