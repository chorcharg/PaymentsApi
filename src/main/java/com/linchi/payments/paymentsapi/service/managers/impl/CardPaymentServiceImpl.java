package com.linchi.payments.paymentsapi.service.managers.impl;

import com.linchi.payments.paymentsapi.dto.request.CardPaymentReq;
import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;
import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;
import com.linchi.payments.paymentsapi.repository.CardRepository;
import com.linchi.payments.paymentsapi.service.Authorizers.PaymentAuthService;
import com.linchi.payments.paymentsapi.service.managers.PaymentManagerService;
import com.linchi.payments.paymentsapi.service.support.AuthServiceFactory;
import com.linchi.payments.paymentsapi.service.support.BussinesResultEnum;
import com.linchi.payments.paymentsapi.service.support.Mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CardPaymentServiceImpl implements PaymentManagerService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    private AuthServiceFactory authServiceFactory;

    @Override
    public PaymentResp processPayment(PaymentReq paymentReq) {

        CardPaymentReq cardPaymentReq = (CardPaymentReq) paymentReq;

        //reglas.....


        PaymentAuthService paymentAuthService =
                this.authServiceFactory
                .getPaymentAuthService(cardPaymentReq.getAuthorizer());

        if (paymentAuthService == null) {
            return Mappers.mapPayReqToPayResp(
                            paymentReq,
                            PaymentStatusEnum.ERROR,
                            BussinesResultEnum.INVALID_AUTHORIZER.getDescription()
                    );
        }


        return paymentAuthService.doPayment(cardPaymentReq);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveTransaction(PaymentReq paymentReq) {
        this.cardRepository.save(Mappers.mapCardPayReqToCardEntity(paymentReq));
    }

}
