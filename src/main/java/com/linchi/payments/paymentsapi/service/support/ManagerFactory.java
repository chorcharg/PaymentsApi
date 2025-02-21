package com.linchi.payments.paymentsapi.service.support;

import com.linchi.payments.paymentsapi.dto.request.CardPaymentReq;
import com.linchi.payments.paymentsapi.dto.request.P2pPaymentReq;
import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.request.TransferPaymentReq;
import com.linchi.payments.paymentsapi.excpetions.FactoryException;
import com.linchi.payments.paymentsapi.service.managers.PaymentManagerService;
import com.linchi.payments.paymentsapi.service.managers.impl.CardPaymentServiceImpl;
import com.linchi.payments.paymentsapi.service.managers.impl.P2pPaymentServiceImpl;
import com.linchi.payments.paymentsapi.service.managers.impl.TransferPaymentServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ManagerFactory {

    private final ApplicationContext context;

    @Autowired
    public ManagerFactory(ApplicationContext context) {
        this.context = context;
    }

    //tomamos los servicios del contexto de spring, para que los use como beans
    public PaymentManagerService getPaymentMethod(PaymentReq paymentReq) {
        if (paymentReq instanceof CardPaymentReq) {
            return context.getBean(CardPaymentServiceImpl.class);
        }

        if (paymentReq instanceof TransferPaymentReq) {
            return context.getBean(TransferPaymentServiceImpl.class);
        }

        if(paymentReq instanceof P2pPaymentReq) {
            return context.getBean(P2pPaymentServiceImpl.class);
        }

        throw new FactoryException(this.getClass().toString(), paymentReq);

    }
}
