package com.linchi.payments.paymentsapi.service.managers.impl;

import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;
import com.linchi.payments.paymentsapi.repository.CardRepository;
import com.linchi.payments.paymentsapi.service.managers.PaymentManagerService;
import com.linchi.payments.paymentsapi.service.support.AuthServiceFactory;
import com.linchi.payments.paymentsapi.service.support.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CardPaymentServiceImpl implements PaymentManagerService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    private AuthServiceFactory authServiceFactory;

    @Override
    public ResponseEntity<PaymentResp> processPayment(PaymentReq paymentReq) {

        //TO_DO: Reglas de negocio propias del tipo de pago, por ahora solo llama al authorizador

        return this.authServiceFactory
                .getPaymentAuthService(paymentReq)
                .doPayment(paymentReq);
    }

    public void saveTransaction(PaymentReq paymentReq) {
        this.cardRepository.save(Mappers.mapCardPayReqToCardEntity(paymentReq));

    }
}
