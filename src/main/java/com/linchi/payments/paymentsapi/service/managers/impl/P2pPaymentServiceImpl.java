package com.linchi.payments.paymentsapi.service.managers.impl;

import com.linchi.payments.paymentsapi.dto.request.P2pPaymentReq;
import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;
import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;
import com.linchi.payments.paymentsapi.repository.P2pRepository;
import com.linchi.payments.paymentsapi.service.Authorizers.PaymentAuthService;
import com.linchi.payments.paymentsapi.service.managers.PaymentManagerService;
import com.linchi.payments.paymentsapi.service.support.AuthServiceFactory;
import com.linchi.payments.paymentsapi.service.support.BussinesResultEnum;
import com.linchi.payments.paymentsapi.service.support.Mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class P2pPaymentServiceImpl implements PaymentManagerService {

    @Autowired
    P2pRepository p2pRepository;

    @Autowired
    private AuthServiceFactory authServiceFactory;

    @Override
    public PaymentResp processPayment(PaymentReq paymentReq) {

        P2pPaymentReq p2pPaymentReq = (P2pPaymentReq) paymentReq;

        PaymentAuthService paymentAuthService =
                this.authServiceFactory
                        .getPaymentAuthService("P2P");

        if (paymentAuthService == null) {
            return Mappers.mapPayReqToPayResp(
                    paymentReq,
                    PaymentStatusEnum.ERROR,
                    BussinesResultEnum.INVALID_AUTHORIZER.getDescription()
            );
        }

        return paymentAuthService.doPayment(p2pPaymentReq);
    }

    @Override
    public void saveTransaction(PaymentReq paymentReq) {
        this.p2pRepository.save(Mappers.mapP2pPayReqToP2pEntity(paymentReq));

    }
}
