package com.linchi.payments.paymentsapi.service.managers.impl;

import com.linchi.payments.paymentsapi.dto.request.P2pPaymentReq;
import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;
import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;
import com.linchi.payments.paymentsapi.repository.P2pRepository;
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
public class P2pPaymentServiceImpl implements PaymentManagerService {


    private final P2pRepository p2pRepository;
    private final AuthServiceFactory authServiceFactory;

    @Autowired
    public P2pPaymentServiceImpl(P2pRepository p2pRepository, AuthServiceFactory authServiceFactory) {
        this.p2pRepository = p2pRepository;
        this.authServiceFactory = authServiceFactory;
    }

    @Override
    public PaymentResp processPayment(PaymentReq paymentReq) {

        P2pPaymentReq p2pPaymentReq = (P2pPaymentReq) paymentReq;

        PaymentAuthService paymentAuthService =
                this.authServiceFactory
                        .getPaymentAuthService(AuthsEnum.P2P);

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
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveTransaction(PaymentReq paymentReq) {
        this.p2pRepository.save(Mappers.mapP2pPayReqToP2pEntity(paymentReq));

    }

    @Override
    public ManagersEnum getManager() {
        return ManagersEnum.P2pManager;
    }
}
