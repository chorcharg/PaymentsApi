package com.linchi.payments.paymentsapi.excpetions;

import lombok.Getter;

@Getter
public enum ExceptionEnum {

    OK("00","Operacion exitosa"),
    PAYMENT_EXISTS("10","El ID de intencion de pago ya existe"),
    INSUFFICIENT_BALANCE("11","Saldo Insuficiente"),
    INVALID_CARD("11","Saldo Insuficiente");

    private final String code;
    private final String description;


    private ExceptionEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }


}
