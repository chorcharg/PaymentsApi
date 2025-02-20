package com.linchi.payments.paymentsapi.service.support;

import com.linchi.payments.paymentsapi.dto.request.CardPaymentReq;
import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.request.TransferPaymentReq;
import com.linchi.payments.paymentsapi.service.Authorizers.PaymentAuthService;

import com.linchi.payments.paymentsapi.service.Authorizers.impl.CardAuthLinchiServiceImpl;
import com.linchi.payments.paymentsapi.service.Authorizers.impl.UserIdPaymentAuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceFactory {

    private final ApplicationContext context;

    @Autowired
    public AuthServiceFactory(ApplicationContext context) {
        this.context = context;
    }

    public PaymentAuthService getPaymentAuthService(PaymentReq paymentReq) {


        if (paymentReq instanceof CardPaymentReq){
            return context.getBean(CardAuthLinchiServiceImpl.class);
        } else if(paymentReq instanceof TransferPaymentReq){
            return context.getBean(UserIdPaymentAuthServiceImpl.class);
        }

        throw new IllegalArgumentException("No se encontraron Servicios para el Request: " + paymentReq.getClass().getSimpleName());
    }

}
