package com.linchi.payments.paymentsapi.service.Authorizers.impl;

import com.linchi.payments.paymentsapi.dto.request.CardPaymentReq;
import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;
import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;
import com.linchi.payments.paymentsapi.service.Authorizers.PaymentAuthService;
import com.linchi.payments.paymentsapi.service.support.enums.AuthsEnum;
import com.linchi.payments.paymentsapi.service.support.enums.BussinesResultEnum;
import com.linchi.payments.paymentsapi.service.support.Mappers;
import org.springframework.stereotype.Service;

@Service
public class CardAuthBismaServiceImpl implements PaymentAuthService {

    @Override
    public PaymentResp doPayment(PaymentReq paymentReq) {

        CardPaymentReq cardPaymentReq = (CardPaymentReq)paymentReq;


        if(cardPaymentReq.getAmount() == 321){
            return Mappers.mapPayReqToPayResp(
                    paymentReq,
                    PaymentStatusEnum.REJECTED,
                    BussinesResultEnum.INSUFFICIENT_BALANCE.getDescription()
            );

            //  throw new BusinessException(BussinesResultEnum.INSUFFICIENT_BALANCE, paymentReq);
        }

        if(cardPaymentReq.getCardNumber().equals("321")){

            return Mappers.mapPayReqToPayResp(
                    paymentReq,
                    PaymentStatusEnum.ERROR,
                    BussinesResultEnum.INVALID_CARD.getDescription()
            );

            //  throw new BusinessException(BussinesResultEnum.INVALID_CARD, paymentReq);
        }

        return Mappers.mapPayReqToPayResp(
                paymentReq,
                PaymentStatusEnum.APPROVED,
                BussinesResultEnum.OK.getDescription()
        );

    }

    @Override
    public AuthsEnum getAuth() {
        return AuthsEnum.BISMA;
    }
}
