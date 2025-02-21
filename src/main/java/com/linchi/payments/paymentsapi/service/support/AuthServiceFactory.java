package com.linchi.payments.paymentsapi.service.support;

import com.linchi.payments.paymentsapi.dto.request.CardPaymentReq;
import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.request.TransferPaymentReq;
import com.linchi.payments.paymentsapi.excpetions.FactoryException;
import com.linchi.payments.paymentsapi.service.Authorizers.PaymentAuthService;
import com.linchi.payments.paymentsapi.service.Authorizers.impl.CardAuthLinchiServiceImpl;
import com.linchi.payments.paymentsapi.service.Authorizers.impl.UserIdPaymentAuthServiceImpl;

import com.linchi.payments.paymentsapi.service.Authorizers.support.AuthsEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthServiceFactory {

    private final ApplicationContext context;
    private final List<PaymentAuthService> paymentAuthService;

    @Autowired
    public AuthServiceFactory(ApplicationContext context, List<PaymentAuthService> paymentAuthService) {
        this.context = context;
        this.paymentAuthService = paymentAuthService;
    }

    public PaymentAuthService getPaymentAuthService(String provider) {

        AuthsEnum payAuth = AuthsEnum.fromName(provider);


        return paymentAuthService
                .stream()
                .filter(
                        paymentAuthService ->  paymentAuthService.getAuth() == payAuth
                )
                .findFirst()
                .orElse(null);
    }



    public PaymentAuthService getPaymentAuthService(PaymentReq paymentReq) {

        if (paymentReq instanceof CardPaymentReq){
            return context.getBean(CardAuthLinchiServiceImpl.class);

        } else if(paymentReq instanceof TransferPaymentReq){
            return context.getBean(UserIdPaymentAuthServiceImpl.class);
        }

        throw new FactoryException(this.getClass().toString(), paymentReq);
    }

}
