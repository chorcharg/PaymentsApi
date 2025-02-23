package com.linchi.payments.paymentsapi.service.managers.impl;

import com.linchi.payments.paymentsapi.dto.request.CardPaymentReq;
import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;
import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;
import com.linchi.payments.paymentsapi.repository.CardRepository;
import com.linchi.payments.paymentsapi.service.Authorizers.PaymentAuthService;
import com.linchi.payments.paymentsapi.service.managers.PaymentManagerService;
import com.linchi.payments.paymentsapi.service.support.*;

import com.linchi.payments.paymentsapi.service.support.enums.AuthsEnum;
import com.linchi.payments.paymentsapi.service.support.enums.BussinesResultEnum;
import com.linchi.payments.paymentsapi.service.support.enums.ManagersEnum;
import com.linchi.payments.paymentsapi.service.support.factorys.AuthServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CardPaymentServiceImpl implements PaymentManagerService {


    private final CardRepository cardRepository;
    private final AuthServiceFactory authServiceFactory;

    @Autowired
    public CardPaymentServiceImpl(CardRepository cardRepository, AuthServiceFactory authServiceFactory) {
        this.cardRepository = cardRepository;
        this.authServiceFactory = authServiceFactory;
    }

    @Override
    public PaymentResp processPayment(PaymentReq paymentReq) {

        CardPaymentReq cardPaymentReq = (CardPaymentReq) paymentReq;

        //reglas.....


        PaymentAuthService paymentAuthService =
                this.authServiceFactory
                .getPaymentAuthService(
                        AuthsEnum
                        .fromName(cardPaymentReq.getAuthorizer())
                        );

        if (paymentAuthService == null) {
            return Mappers.mapPayReqToPayResp(
                            paymentReq,
                            PaymentStatusEnum.ERROR,
                            BussinesResultEnum.INVALID_AUTHORIZER.getDescription()
                    );
        }


        return paymentAuthService.doPayment(cardPaymentReq);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveTransaction(PaymentReq paymentReq) {
        this.cardRepository.save(Mappers.mapCardPayReqToCardEntity(paymentReq));
    }

    @Override
    public ManagersEnum getManager() {
        return ManagersEnum.CardManager;
    }

}
