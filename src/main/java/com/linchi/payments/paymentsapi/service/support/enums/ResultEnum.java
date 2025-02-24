package com.linchi.payments.paymentsapi.service.support.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResultEnum {

    OK("000","Operacion exitosa", HttpStatus.OK),
    PAYMENT_FOUND("001","Pago encontrado", HttpStatus.OK),
    INSUFFICIENT_BALANCE("012","Saldo Insuficiente", HttpStatus.OK),
    INVALID_CARD("013","Tarjeta Invalida", HttpStatus.OK),
    PAYMENT_NOT_FOUND("014","El identificador de pago no existe", HttpStatus.NOT_FOUND),

    INVALID_FIELDS("015","Atributo inexistente para ordenar la consulta", HttpStatus.BAD_REQUEST),
    PAYMENT_EXISTS("106","El ID de intencion de pago ya existe", HttpStatus.FORBIDDEN),
    INVALID_AUTHORIZER("101","Sin proveedor de autorizacion", HttpStatus.FORBIDDEN),
    INVALID_PAY_METHOD("102","Sin proveedor de autorizacion", HttpStatus.FORBIDDEN),
    INVALID_USER("103","Usuario Invalido", HttpStatus.FORBIDDEN),
    INVALID_BANK_CODE("104","El identificador de banco no es valido", HttpStatus.FORBIDDEN),
    DATA_CONVERT_ERROR("105","Los valores del request no cumplen con los requisitos", HttpStatus.FORBIDDEN),

    VERIFY_STATUS("201","Pago cursado pero no se pudo responder el estado. ", HttpStatus.INTERNAL_SERVER_ERROR)
    ;


    private final String code;
    private final String description;
    private final HttpStatus httpStatus;

    ResultEnum(String code, String description, HttpStatus httpStatus) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }



}
