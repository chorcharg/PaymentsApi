package com.linchi.payments.paymentsapi.controller.impl;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.linchi.payments.paymentsapi.excpetions.BusinessException;
import com.linchi.payments.paymentsapi.excpetions.InternalException;
import com.linchi.payments.paymentsapi.excpetions.InvalidFindFieldException;
import com.linchi.payments.paymentsapi.excpetions.FactoryException;
import com.linchi.payments.paymentsapi.dto.ExceptionDTO;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;

import com.linchi.payments.paymentsapi.service.support.enums.InternalResultEnum;


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

        String message = ex.getMessage();
        if(message.contains("[ASC, DESC]")){
            message = "[ASC, DESC]";
        }

        ExceptionDTO  exception = new ExceptionDTO();
        exception.setCode(InternalResultEnum.DATA_CONVERT_ERROR.getCode());
        exception.setMessage( InternalResultEnum.DATA_CONVERT_ERROR.getDescription() +": " + message);

        return new ResponseEntity<>(exception, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<PaymentResp> BussinesExceptionHandler(BusinessException ex) {
        PaymentResp paymentResp  = new PaymentResp();
        paymentResp.setResult(ex.getResult());
        paymentResp.setResultDescription(
                ex.getResult().getDescription()
        );

        return new ResponseEntity<>(paymentResp, HttpStatus.OK);
    }

    @ExceptionHandler(InvalidFindFieldException.class)
    public ResponseEntity<ExceptionDTO> InvalidFindFieldExceptionHandler(InvalidFindFieldException e) {

        ExceptionDTO exception =
                ExceptionDTO.builder()
                        .code(InternalResultEnum.INVALID_FIELDS.getCode())
                        .message(
                                InternalResultEnum.INVALID_FIELDS.getDescription()+
                                        e.getMessage()
                        )
                        .build();
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<ExceptionDTO> InternalExceptionHandler(InternalException ex) {
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

}