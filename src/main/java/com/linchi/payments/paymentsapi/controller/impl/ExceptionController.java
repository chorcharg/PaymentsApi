package com.linchi.payments.paymentsapi.controller.impl;

import com.linchi.payments.paymentsapi.dto.exceptions.ExceptionDTO;
import com.linchi.payments.paymentsapi.excpetions.BusinessException;
import com.linchi.payments.paymentsapi.excpetions.FactoryException;

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
    public ResponseEntity<ExceptionDTO> BussinesExceptionHandler(BusinessException ex) {
        ExceptionDTO exception =
                ExceptionDTO.builder()
                .code( ex.getException().getCode())
                .message(ex.getMessage())
                .paymentReq(ex.getPaymentReq())
                .build();
        return new ResponseEntity<ExceptionDTO>(exception, HttpStatus.OK);
    }

    @ExceptionHandler(FactoryException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ExceptionDTO> FactoryExceptionHandler(BusinessException ex) {
        ExceptionDTO exception =
                ExceptionDTO.builder()
                        .paymentReq(ex.getPaymentReq())
                        .build();
        return new ResponseEntity<ExceptionDTO>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}