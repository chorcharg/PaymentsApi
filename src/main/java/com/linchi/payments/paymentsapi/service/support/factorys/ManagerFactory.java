package com.linchi.payments.paymentsapi.service.support.factorys;

import com.linchi.payments.paymentsapi.service.managers.PaymentManagerService;

import com.linchi.payments.paymentsapi.service.support.enums.ManagersEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ManagerFactory {


    private final List<PaymentManagerService> paymentManagerService;

    @Autowired
    public ManagerFactory(List<PaymentManagerService> paymentManagerService) {

        this.paymentManagerService = paymentManagerService;
    }

    public PaymentManagerService getPaymentMethod(ManagersEnum manager) {

        return paymentManagerService
                .stream()
                .filter(
                        paymentManagerService -> paymentManagerService.getManager() == manager
                )
                .findFirst()
                .orElse(null);

    }



}
