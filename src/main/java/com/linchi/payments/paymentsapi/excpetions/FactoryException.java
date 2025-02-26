package com.linchi.payments.paymentsapi.excpetions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FactoryException extends RuntimeException{

    private String className;

    public FactoryException( String className) {
        this.className = className;
    }
}
