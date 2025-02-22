package com.linchi.payments.paymentsapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ExceptionDTO <T>{

    private String code;
    private String message;
    private T request;

}
