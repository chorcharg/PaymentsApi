package com.linchi.payments.paymentsapi.service.support.enums;

import lombok.Getter;

@Getter
public enum BussinesResultEnum {

    OK("00","Operacion exitosa"),
    PAYMENT_EXISTS("10","El ID de intencion de pago ya existe"),
    INSUFFICIENT_BALANCE("11","Saldo Insuficiente"),
    INVALID_CARD("12","Tarjeta Invalida"),
    INVALID_AUTHORIZER("13","Sin proveedor de autorizacion"),
    INVALID_USER("14","Usuario Invalido"),
    INVALID_BANK_CODE("15","El identificador de banco no es valido"),
    PAYMENT_NOT_FOUND("16","El identificador de pago no existe"),
    PAYMENT_FOUND("01","Pago encontrado"),
    ;


    private final String code;
    private final String description;

    private BussinesResultEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

}
