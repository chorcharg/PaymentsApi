package com.linchi.payments.paymentsapi.service.support;

import com.linchi.payments.paymentsapi.dto.request.CardPaymentReq;
import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.request.TransferPaymentReq;
import com.linchi.payments.paymentsapi.service.Authorizers.PaymentAuthService;
import com.linchi.payments.paymentsapi.service.Authorizers.impl.CardAuthLinchiServiceImpl;
import com.linchi.payments.paymentsapi.service.Authorizers.impl.UserIdPaymentAuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceFactory {

    @Autowired
    private CardAuthLinchiServiceImpl cardPaymentAuthService;

    @Autowired
    private UserIdPaymentAuthServiceImpl trasnferPaymentAuthService;

    public PaymentAuthService getPaymentAuthService(PaymentReq paymentReq) {


        if (paymentReq instanceof CardPaymentReq){
            return cardPaymentAuthService;
        } else if(paymentReq instanceof TransferPaymentReq){
            return trasnferPaymentAuthService;
        }

        throw new IllegalArgumentException("No se encontraron Servicios para el Request: " + paymentReq.getClass().getSimpleName());
    }

}
