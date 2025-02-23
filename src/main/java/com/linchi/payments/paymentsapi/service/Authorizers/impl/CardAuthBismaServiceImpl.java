package com.linchi.payments.paymentsapi.service.Authorizers.impl;

import com.linchi.payments.paymentsapi.dto.PaymentDTO;
import com.linchi.payments.paymentsapi.entitys.CardPayment;
import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;
import com.linchi.payments.paymentsapi.excpetions.BusinessException;
import com.linchi.payments.paymentsapi.service.Authorizers.PaymentAuthService;
import com.linchi.payments.paymentsapi.service.support.enums.AuthsEnum;
import com.linchi.payments.paymentsapi.service.support.enums.ResultEnum;
import org.springframework.stereotype.Service;

@Service
public class CardAuthBismaServiceImpl implements PaymentAuthService {

    @Override
    public void doPayment(PaymentDTO paymentDTO) {

        //simulamos llamadas al provedoor de autorizacion

        //aprovechamos CardPayment, pero en este punto
        // ya estariamos mapeando un request body segun interfaz del proveedor

        CardPayment cardPayment = (CardPayment) paymentDTO.getMethod();

        //tarjeta invalida
        if(cardPayment.getCardNumber().equals("123")){
            paymentDTO.getPayment().setStatus(PaymentStatusEnum.REJECTED);
            paymentDTO.setResult(ResultEnum.INVALID_CARD);
            paymentDTO.getPayment().setDescription(ResultEnum.INVALID_CARD.getDescription());
            throw new BusinessException(ResultEnum.INVALID_CARD);

        }

        //suponemos que este proveedor solo trabaja moneda local
        //enviamos el importe convertido
        //saldo insuficiente
        if(paymentDTO.getPayment().getLocalAmount() >100000){
            paymentDTO.getPayment().setStatus(PaymentStatusEnum.REJECTED);
            paymentDTO.getPayment().setDescription(ResultEnum.INSUFFICIENT_BALANCE.getDescription());
            paymentDTO.setResult(ResultEnum.INSUFFICIENT_BALANCE);
            throw new BusinessException(ResultEnum.INSUFFICIENT_BALANCE);
        }


        //suponemos que llamamos y salio todo bien
        paymentDTO.getPayment().setStatus(PaymentStatusEnum.APPROVED);
        paymentDTO.setResult(ResultEnum.OK);
    }

    @Override
    public AuthsEnum getAuth() {
        return AuthsEnum.BISMA;
    }
}
