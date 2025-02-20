package com.linchi.payments.paymentsapi.controller.impl;

import com.linchi.payments.paymentsapi.dto.exceptions.ExceptionDTO;
import com.linchi.payments.paymentsapi.excpetions.BussinesException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionsController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> validationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }

    @ExceptionHandler(BussinesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionDTO> BussinesExceptionHandler(BussinesException ex) {
        ExceptionDTO exception =
                ExceptionDTO.builder()
                .code( ex.getException().getCode())
                .message(ex.getMessage())
                .paymentReq(ex.getPaymentReq())
                .build();
        return new ResponseEntity<ExceptionDTO>(exception, HttpStatus.BAD_REQUEST);
    }


}