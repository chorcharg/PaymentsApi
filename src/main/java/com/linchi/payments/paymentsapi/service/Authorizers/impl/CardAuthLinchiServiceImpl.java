package com.linchi.payments.paymentsapi.service.Authorizers.impl;

import com.linchi.payments.paymentsapi.dto.request.CardPaymentReq;
import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;
import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;
import com.linchi.payments.paymentsapi.excpetions.BusinessException;
import com.linchi.payments.paymentsapi.service.support.BussinesResultEnum;
import com.linchi.payments.paymentsapi.service.Authorizers.PaymentAuthService;
import com.linchi.payments.paymentsapi.service.support.Mappers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CardAuthLinchiServiceImpl implements PaymentAuthService {

    @Override
    public ResponseEntity<PaymentResp> doPayment(PaymentReq paymentReq) {

        CardPaymentReq cardPaymentReq = (CardPaymentReq)paymentReq;

        //FIX_ME: al lanzar el throw, no persiste en DB resultado (queda started) ni descripcion
        //se puede capturar el error en el manager?
        //sino construir el response entity y largar la excpecion desde mas arriba
        if(cardPaymentReq.getAmount() > 10000){
            throw new BusinessException(BussinesResultEnum.INSUFFICIENT_BALANCE, paymentReq);
        }

        if(cardPaymentReq.getCardNumber().equals("123")){
            throw new BusinessException(BussinesResultEnum.INVALID_CARD, paymentReq);
        }

        return new ResponseEntity<PaymentResp>(
                Mappers.mapPayReqToPayResp(
                        paymentReq,
                        PaymentStatusEnum.FINISHED,
                        BussinesResultEnum.OK.getDescription()),
                HttpStatus.OK
        );
    }

}
