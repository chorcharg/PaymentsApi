package com.linchi.payments.paymentsapi.service.managers.impl;

import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.request.TransferPaymentReq;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;
import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;
import com.linchi.payments.paymentsapi.repository.TransferRepository;
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
public class TransferPaymentServiceImpl implements PaymentManagerService {


    private final AuthServiceFactory authServiceFactory;
    private final TransferRepository transferRepository;

    @Autowired
    public TransferPaymentServiceImpl(AuthServiceFactory authServiceFactory, TransferRepository transferRepository) {
        this.authServiceFactory = authServiceFactory;
        this.transferRepository = transferRepository;
    }

    @Override
    public PaymentResp processPayment(PaymentReq paymentReq) {

        TransferPaymentReq transferPaymentReq = (TransferPaymentReq) paymentReq;

        PaymentAuthService paymentAuthService =
                this.authServiceFactory
                        .getPaymentAuthService(AuthsEnum.TRANSFER);

        if (paymentAuthService == null) {
            return Mappers.mapPayReqToPayResp(
                    paymentReq,
                    PaymentStatusEnum.ERROR,
                    BussinesResultEnum.INVALID_AUTHORIZER.getDescription()
            );
        }

        return paymentAuthService.doPayment(transferPaymentReq);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveTransaction(PaymentReq paymentReq) {
        this.transferRepository.save(Mappers.mapTransfPayReqToTransfEntity(paymentReq));

    }

    @Override
    public ManagersEnum getManager() {
        return ManagersEnum.TransferManager;
    }
}
