package com.linchi.payments.paymentsapi.excpetions;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.linchi.payments.paymentsapi.service.support.enums.BusinessResultEnum;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException{

    private final BusinessResultEnum result;

    public BusinessException(BusinessResultEnum result) {
        this.result = result;
    }

}
