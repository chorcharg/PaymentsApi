package com.linchi.payments.paymentsapi.service.support.enums;


import lombok.Getter;

@Getter
public enum InternalResultEnum {

    DATA_CONVERT_ERROR("105","Los valores del request no cumplen con los requisitos"), ///,
    PAYMENT_NOT_FOUND("014","El identificador de pago no existe" ), ///
    PAYMENT_FOUND("001","Pago encontrado"),  ///
    INVALID_FIELDS("015","Atributo inexistente para ordenar la consulta" ), ///
    PAYMENT_EXISTS("106","El ID de intencion de pago ya existe" ), ///
    VERIFY_STATUS("201","Pago cursado pero no se pudo responder el estado. " ) ///
    ;

    private final String code;
    private final String description;


    InternalResultEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
