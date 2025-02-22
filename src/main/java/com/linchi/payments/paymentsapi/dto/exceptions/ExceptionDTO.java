package com.linchi.payments.paymentsapi.dto.exceptions;

import com.linchi.payments.paymentsapi.dto.request.PaymentReq;

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
