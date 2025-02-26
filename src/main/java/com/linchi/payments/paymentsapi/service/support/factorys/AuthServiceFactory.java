package com.linchi.payments.paymentsapi.service.support.factorys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.linchi.payments.paymentsapi.service.authorizers.PaymentAuthService;
import com.linchi.payments.paymentsapi.service.support.enums.AuthsEnum;


@Component
public class AuthServiceFactory {


    private final List<PaymentAuthService> paymentAuthService;

    @Autowired
    public AuthServiceFactory(List<PaymentAuthService> paymentAuthService) {
        this.paymentAuthService = paymentAuthService;
    }

    public PaymentAuthService getPaymentAuthService(AuthsEnum provider) {
        return paymentAuthService
                .stream()
                .filter(
                        paymentAuthService ->  paymentAuthService.getAuth() == provider
                )
                .findFirst()
                .orElse( null );
    }

}
