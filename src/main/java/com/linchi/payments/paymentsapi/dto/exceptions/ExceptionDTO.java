package com.linchi.payments.paymentsapi.dto.exceptions;


import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionDTO {

    private String code;
    private String message;
    private PaymentReq paymentReq;



}
