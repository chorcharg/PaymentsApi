package com.linchi.payments.paymentsapi.service.support.enums;

import lombok.Getter;

import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;

@Getter
public enum BusinessResultEnum {

    OK("000","Operacion exitosa", PaymentStatusEnum.APPROVED ),

    INSUFFICIENT_BALANCE("012","Saldo Insuficiente" , PaymentStatusEnum.REJECTED),
    INVALID_CARD("013","Tarjeta Invalida" , PaymentStatusEnum.REJECTED),
    INVALID_USER("103","Usuario Invalido", PaymentStatusEnum.REJECTED),

    INVALID_AUTHORIZER("101","Sin proveedor de autorizacion", PaymentStatusEnum.FAILED),
    INVALID_PAY_METHOD("102","Sin proveedor de autorizacion" , PaymentStatusEnum.FAILED),
    INVALID_BANK_CODE("104","El identificador de banco no es valido", PaymentStatusEnum.FAILED),

    ;

    private final String code;
    private final String description;
    private final PaymentStatusEnum status;

    BusinessResultEnum(String code, String description, PaymentStatusEnum status) {
        this.code = code;
        this.description = description;
        this.status = status;
    }

}
