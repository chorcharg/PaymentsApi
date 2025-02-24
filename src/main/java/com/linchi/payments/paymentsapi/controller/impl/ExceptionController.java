package com.linchi.payments.paymentsapi.controller.impl;

import com.linchi.payments.paymentsapi.dto.ExceptionDTO;

import com.linchi.payments.paymentsapi.dto.response.PaymentResp;
import com.linchi.payments.paymentsapi.excpetions.*;

import com.linchi.payments.paymentsapi.service.support.enums.ResultEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
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

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<ExceptionDTO> HttpMessageConversionExceptionHandler(HttpMessageConversionException ex) {
        ExceptionDTO  exception = new ExceptionDTO();
        exception.setCode(ResultEnum.DATA_CONVERT_ERROR.getCode());
        exception.setMessage( ResultEnum.DATA_CONVERT_ERROR.getDescription() +": " + ex.getMessage());

        return new ResponseEntity<>(exception, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<PaymentResp> BussinesExceptionHandler(BusinessException ex) {
        PaymentResp paymentResp  = new PaymentResp();
        paymentResp.setResult(ex.getResult());
        paymentResp.setResultDescription(ex.getResult().getDescription());

        return new ResponseEntity<>(paymentResp, HttpStatus.OK);
    }

    @ExceptionHandler(DuplicatePayException.class)

    public ResponseEntity<ExceptionDTO> DuplicatePayExceptionHandler(DuplicatePayException ex) {
        ExceptionDTO exception = new ExceptionDTO();
        exception.setCode(
                ex.getResult().getCode()
        );
        exception.setMessage(
                ex.getResult().getDescription()
        );

        return new ResponseEntity<>(exception, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(FactoryException.class)
    public ResponseEntity<ExceptionDTO> FactoryExceptionHandler(FactoryException ignored) {
        ExceptionDTO exception =
                ExceptionDTO.builder()
                        .code("999")
                        .message("No se puede realizar la operacion")
                        .build();
        return new ResponseEntity<>(exception, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(PaymentsNotFoundException.class)
    public ResponseEntity<ExceptionDTO> PaymentsNotFoundExceptionHandler(PaymentsNotFoundException ignored) {
        ExceptionDTO exception =
                ExceptionDTO.builder()
                        .code(ResultEnum.PAYMENT_NOT_FOUND.getCode())
                        .message(ResultEnum.PAYMENT_NOT_FOUND.getDescription())
                        .build();
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(InvalidFindFieldException.class)
    public ResponseEntity<ExceptionDTO> InvalidFindFieldExceptionHandler(InvalidFindFieldException e) {
        ExceptionDTO exception =
                ExceptionDTO.builder()
                        .code(ResultEnum.INVALID_FIELDS.getCode())
                        .message(
                                ResultEnum.INVALID_FIELDS.getDescription()+
                                e.getMessage()
                        )
                        .build();
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }




}