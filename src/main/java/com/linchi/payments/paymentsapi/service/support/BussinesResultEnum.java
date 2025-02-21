package com.linchi.payments.paymentsapi.service.support;

import lombok.Getter;

@Getter
public enum BussinesResultEnum {

    OK("00","Operacion exitosa"),
    PAYMENT_EXISTS("10","El ID de intencion de pago ya existe"),
    INSUFFICIENT_BALANCE("11","Saldo Insuficiente"),
    INVALID_CARD("11","Tarjeta Invalida");

    private final String code;
    private final String description;

    private BussinesResultEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

}
