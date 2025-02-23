package com.linchi.payments.paymentsapi.service.Authorizers.impl;

import com.linchi.payments.paymentsapi.dto.PaymentDTO;
import com.linchi.payments.paymentsapi.entitys.P2pPayment;
import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;
import com.linchi.payments.paymentsapi.excpetions.BusinessException;
import com.linchi.payments.paymentsapi.service.Authorizers.PaymentAuthService;

import com.linchi.payments.paymentsapi.service.support.enums.AuthsEnum;
import com.linchi.payments.paymentsapi.service.support.enums.ResultEnum;
import org.springframework.stereotype.Service;

@Service
public class P2pAuthMeliServiceImpl implements PaymentAuthService {

    @Override
    public void doPayment(PaymentDTO paymentDTO) {

        P2pPayment p2pPayment = (P2pPayment) paymentDTO.getMethod();

        if(p2pPayment.getSenderId()==123){
            paymentDTO.getPayment().setStatus(PaymentStatusEnum.REJECTED);
            paymentDTO.setResult(ResultEnum.INVALID_USER);
            paymentDTO.getPayment().setDescription(ResultEnum.INVALID_USER.getDescription());
            throw new BusinessException(ResultEnum.INVALID_USER);
        }

/*        P2pPaymentReq p2pPaymentReq = (P2pPaymentReq)paymentReq;

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
        */

        //suponemos que llamamos y salio todo bien
        paymentDTO.getPayment().setStatus(PaymentStatusEnum.APPROVED);
        paymentDTO.setResult(ResultEnum.OK);
    }

    @Override
    public AuthsEnum getAuth() {
        return AuthsEnum.P2P;
    }

}
