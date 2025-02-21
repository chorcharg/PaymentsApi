package com.linchi.payments.paymentsapi.service.Authorizers.impl;

import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.request.TransferPaymentReq;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;
import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;
import com.linchi.payments.paymentsapi.service.Authorizers.PaymentAuthService;
import com.linchi.payments.paymentsapi.service.Authorizers.support.AuthsEnum;
import com.linchi.payments.paymentsapi.service.support.BussinesResultEnum;
import com.linchi.payments.paymentsapi.service.support.Mappers;
import org.springframework.stereotype.Service;

@Service
public class TrasnferAuthLinchiServiceImpl implements PaymentAuthService {
    @Override
    public PaymentResp doPayment(PaymentReq paymentReq) {
        TransferPaymentReq transferPaymentReq = (TransferPaymentReq) paymentReq;

        if(transferPaymentReq.getBankCode().equals("PARIBAS")) {
            return Mappers.mapPayReqToPayResp(
                    paymentReq,
                    PaymentStatusEnum.ERROR,
                    BussinesResultEnum.INVALID_BANK_CODE.getDescription()
            );
        }


        return Mappers.mapPayReqToPayResp(
                paymentReq,
                PaymentStatusEnum.APPROVED,
                BussinesResultEnum.OK.getDescription()
        );

    }

    @Override
    public AuthsEnum getAuth() {
        return AuthsEnum.TRANSFER;
    }
}
