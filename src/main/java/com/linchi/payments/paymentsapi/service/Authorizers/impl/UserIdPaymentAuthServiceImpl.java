package com.linchi.payments.paymentsapi.service.Authorizers.impl;

import com.linchi.payments.paymentsapi.dto.request.CardPaymentReq;
import com.linchi.payments.paymentsapi.dto.request.P2pPaymentReq;
import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;
import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;
import com.linchi.payments.paymentsapi.service.Authorizers.PaymentAuthService;

import com.linchi.payments.paymentsapi.service.Authorizers.support.AuthsEnum;
import com.linchi.payments.paymentsapi.service.support.BussinesResultEnum;
import com.linchi.payments.paymentsapi.service.support.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserIdPaymentAuthServiceImpl implements PaymentAuthService {

    @Override
    public PaymentResp doPayment(PaymentReq paymentReq) {

        P2pPaymentReq p2pPaymentReq = (P2pPaymentReq)paymentReq;

        if(p2pPaymentReq.getSenderId() == 123){
            return Mappers.mapPayReqToPayResp(
                    paymentReq,
                    PaymentStatusEnum.ERROR,
                    BussinesResultEnum.INVALID_USER.getDescription()
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
        return AuthsEnum.USER_ID;
    }

}
