package com.linchi.payments.paymentsapi.controller.impl;

import com.linchi.payments.paymentsapi.dto.response.ExceptionDTO;
import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.request.PaymentStatusReq;
import com.linchi.payments.paymentsapi.excpetions.BusinessException;
import com.linchi.payments.paymentsapi.excpetions.FactoryException;

import com.linchi.payments.paymentsapi.excpetions.PaymentsNotFoundException;
import com.linchi.payments.paymentsapi.service.support.enums.BussinesResultEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Set<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {

        return  ex.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(error ->
                            String.join("; ",
                                    error.getField(),
                                    error.getCode(),
                                    String.valueOf(error.getRejectedValue())
                            )
                    )
                    .collect(Collectors.toSet());
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ExceptionDTO<PaymentReq>> BussinesExceptionHandler(BusinessException ex) {
        ExceptionDTO exception =
                ExceptionDTO.builder()
                .code(ex.getException().getCode())
                .message(ex.getMessage())
                .request(ex.getPaymentReq())
                .build();
        return new ResponseEntity<ExceptionDTO<PaymentReq>>(exception, HttpStatus.OK);
    }

    @ExceptionHandler(FactoryException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ExceptionDTO<PaymentReq>> FactoryExceptionHandler(FactoryException ex) {
        ExceptionDTO exception =
                ExceptionDTO.builder()
                        .request(ex.getPaymentReq())
                        .build();
        return new ResponseEntity<ExceptionDTO<PaymentReq>>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(PaymentsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionDTO<PaymentStatusReq>> PaymentsNotFoundExceptionHandler(PaymentsNotFoundException ex) {
        ExceptionDTO exception =
                ExceptionDTO.builder()
                        .request(ex.getPaymentStatusReq())
                        .code(BussinesResultEnum.PAYMENT_NOT_FOUND.getCode())
                        .message(BussinesResultEnum.PAYMENT_NOT_FOUND.getDescription())
                        .build();
        return new ResponseEntity<ExceptionDTO<PaymentStatusReq>>(exception, HttpStatus.NOT_FOUND);
    }
}