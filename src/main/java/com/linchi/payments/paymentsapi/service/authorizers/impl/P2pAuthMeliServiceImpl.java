package com.linchi.payments.paymentsapi.service.authorizers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linchi.payments.paymentsapi.dto.PaymentDTO;
import com.linchi.payments.paymentsapi.entitys.P2pPayment;
import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;
import com.linchi.payments.paymentsapi.excpetions.BusinessException;
import com.linchi.payments.paymentsapi.service.authorizers.PaymentAuthService;
import com.linchi.payments.paymentsapi.service.payments.PaymentSupport;
import com.linchi.payments.paymentsapi.service.support.enums.AuthsEnum;
import com.linchi.payments.paymentsapi.service.support.enums.BusinessResultEnum;


@Service
public class P2pAuthMeliServiceImpl implements PaymentAuthService {

    private final PaymentSupport paymentSupport;

    @Autowired
    public P2pAuthMeliServiceImpl(PaymentSupport paymentSupport) {
        this.paymentSupport = paymentSupport;
    }


    @Override
    public void doPayment(PaymentDTO paymentDTO) {

        P2pPayment p2pPayment = (P2pPayment) paymentDTO.getMethod();

        if(p2pPayment.getSenderId()==123){
            this.paymentSupport
                    .updatePaymentDTO(
                            paymentDTO,
                            BusinessResultEnum.INVALID_USER
                    );

            this.paymentSupport.updatePayment(paymentDTO);
            throw new BusinessException(BusinessResultEnum.INVALID_USER);
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
        paymentDTO
                .getPayment()
                .setStatus(PaymentStatusEnum.APPROVED);
        paymentDTO.setResult(BusinessResultEnum.OK);
    }

    @Override
    public AuthsEnum getAuth() {
        return AuthsEnum.P2P;
    }

}
