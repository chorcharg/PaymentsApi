package com.linchi.payments.paymentsapi.service.authorizers.impl;

import com.linchi.payments.paymentsapi.dto.PaymentDTO;
import com.linchi.payments.paymentsapi.entitys.CardPayment;
import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;
import com.linchi.payments.paymentsapi.excpetions.BusinessException;
import com.linchi.payments.paymentsapi.service.payments.PaymentSupport;
import com.linchi.payments.paymentsapi.service.payments.impl.PaymentSupportImpl;
import com.linchi.payments.paymentsapi.service.support.enums.AuthsEnum;
import com.linchi.payments.paymentsapi.service.support.enums.BusinessResultEnum;
import com.linchi.payments.paymentsapi.service.authorizers.PaymentAuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardAuthLinchiServiceImpl implements PaymentAuthService {


    PaymentSupport paymentSupport;
    @Autowired

    public CardAuthLinchiServiceImpl(PaymentSupport paymentSupport) {
        this.paymentSupport = paymentSupport;
    }

    @Override
    public void doPayment(PaymentDTO paymentDTO) {

        PaymentSupportImpl paymentSupport;

        //simulamos llamadas al provedoor de autorizacion

        //aprovechamos CardPayment, pero en este punto
        // ya estariamos mapeando un request body segun interfaz del proveedor

        CardPayment cardPayment = (CardPayment) paymentDTO.getMethod();

        //tarjeta invalida
        if(cardPayment.getCardNumber().equals("321")){

            this.paymentSupport
                    .updatePaymentDTO(
                            paymentDTO,
                            BusinessResultEnum.INVALID_CARD
                    );

            this.paymentSupport.updatePayment(paymentDTO);
            throw new BusinessException(BusinessResultEnum.INVALID_CARD);
        }

        //suponemos que este proveedor trabaja con el importe y moneda original
        //le enviamos la moneda en el request e importe orginial
        //saldo insuficiente
        if(paymentDTO.getPayment().getAmount() >100000){

            this.paymentSupport
                    .updatePaymentDTO(
                            paymentDTO,
                            BusinessResultEnum.INSUFFICIENT_BALANCE
                    );

            this.paymentSupport.updatePayment(paymentDTO);
            throw new BusinessException(BusinessResultEnum.INSUFFICIENT_BALANCE);
        }

        //suponemos que llamamos y salio todo bien
        paymentDTO.getPayment().setStatus(PaymentStatusEnum.APPROVED);
        paymentDTO.setResult(BusinessResultEnum.OK);
    }


    @Override
    public AuthsEnum getAuth() {
        return AuthsEnum.LINCHI;
    }

}
