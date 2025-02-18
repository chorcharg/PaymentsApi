package com.linchi.payments.paymentsapi.service.support;



import com.linchi.payments.paymentsapi.dto.request.CardPaymentReq;
import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.request.TransferPaymentReq;
import com.linchi.payments.paymentsapi.service.managers.PaymentManagerService;
import com.linchi.payments.paymentsapi.service.managers.impl.CardPaymentServiceImpl;
import com.linchi.payments.paymentsapi.service.managers.impl.TransferPaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ManagerFactory {

    @Autowired
    private CardPaymentServiceImpl cardPaymentService;

    @Autowired
    private TransferPaymentServiceImpl transferPaymentService;

    public PaymentManagerService getPaymentMethod(PaymentReq paymentReq) {
        if (paymentReq instanceof CardPaymentReq) {
            return cardPaymentService;
        }

        if (paymentReq instanceof TransferPaymentReq) {
            return transferPaymentService;
        }

        throw new IllegalArgumentException("No se encontraron Servicios para el Request: " + paymentReq.getClass().getSimpleName());

    }
}
