package com.linchi.payments.paymentsapi.service.managers.impl;

import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.request.TransferPaymentReq;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;
import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;
import com.linchi.payments.paymentsapi.repository.TransferRepository;
import com.linchi.payments.paymentsapi.service.Authorizers.PaymentAuthService;
import com.linchi.payments.paymentsapi.service.managers.PaymentManagerService;

import com.linchi.payments.paymentsapi.service.support.AuthServiceFactory;
import com.linchi.payments.paymentsapi.service.support.BussinesResultEnum;
import com.linchi.payments.paymentsapi.service.support.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TransferPaymentServiceImpl implements PaymentManagerService {

    @Autowired
    private AuthServiceFactory authServiceFactory;

    @Autowired
    private TransferRepository transferRepository;

    @Override
    public PaymentResp processPayment(PaymentReq paymentReq) {

        TransferPaymentReq transferPaymentReq = (TransferPaymentReq) paymentReq;

        PaymentAuthService paymentAuthService =
                this.authServiceFactory
                        .getPaymentAuthService("transfer");

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
    public void saveTransaction(PaymentReq paymentReq) {
        this.transferRepository.save(Mappers.mapTransfPayReqToTransfEntity(paymentReq));

    }
}
