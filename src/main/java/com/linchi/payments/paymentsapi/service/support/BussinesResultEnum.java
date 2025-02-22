package com.linchi.payments.paymentsapi.service.support;

import lombok.Getter;

@Getter
public enum BussinesResultEnum {

    OK("00","Operacion exitosa"),
    PAYMENT_EXISTS("10","El ID de intencion de pago ya existe"),
    INSUFFICIENT_BALANCE("11","Saldo Insuficiente"),
    INVALID_CARD("11","Tarjeta Invalida"),
    INVALID_AUTHORIZER("12","Sin proveedor de autorizacion"),
    INVALID_USER("13","Usuario Invalido"),
    INVALID_BANK_CODE("14","El identificador de banco no es valido"),
    PAYMENT_NOT_FOUND("15","El identificador de pago no existe"),
    PAYMENT_FOUND("00","Pago encontrado"),
    ;


    private final String code;
    private final String description;

    private BussinesResultEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

}
