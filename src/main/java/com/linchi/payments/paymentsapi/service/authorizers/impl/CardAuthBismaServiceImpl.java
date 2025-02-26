package com.linchi.payments.paymentsapi.service.authorizers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linchi.payments.paymentsapi.dto.PaymentDTO;
import com.linchi.payments.paymentsapi.entitys.CardPayment;
import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;
import com.linchi.payments.paymentsapi.excpetions.BusinessException;
import com.linchi.payments.paymentsapi.service.authorizers.PaymentAuthService;
import com.linchi.payments.paymentsapi.service.payments.PaymentSupport;
import com.linchi.payments.paymentsapi.service.support.enums.AuthsEnum;
import com.linchi.payments.paymentsapi.service.support.enums.BusinessResultEnum;


@Service
public class CardAuthBismaServiceImpl implements PaymentAuthService {

    private final PaymentSupport paymentSupport;

    @Autowired
    public CardAuthBismaServiceImpl(PaymentSupport paymentSupport) {
        this.paymentSupport = paymentSupport;
    }

    @Override
    public void doPayment(PaymentDTO paymentDTO) {

        //simulamos llamadas al provedoor de autorizacion

        //aprovechamos CardPayment, pero en este punto
        // ya estariamos mapeando un request body segun interfaz del proveedor

        CardPayment cardPayment = (CardPayment) paymentDTO.getMethod();

        //tarjeta invalida
        if(cardPayment.getCardNumber().equals("123")){

            this.paymentSupport
                    .updatePaymentDTO(
                            paymentDTO,
                            BusinessResultEnum.INVALID_CARD
                    );

            this.paymentSupport.updatePayment(paymentDTO);
            throw new BusinessException(BusinessResultEnum.INVALID_CARD);

        }

        //suponemos que este proveedor solo trabaja moneda local
        //enviamos el importe convertido
        //saldo insuficiente

        if(paymentDTO.getPayment().getLocalAmount() >100000){

            this.paymentSupport
                    .updatePaymentDTO(
                            paymentDTO,
                            BusinessResultEnum.INSUFFICIENT_BALANCE
                    );

            this.paymentSupport.updatePayment(paymentDTO);
            throw new BusinessException(BusinessResultEnum.INSUFFICIENT_BALANCE);
        }


        //suponemos que llamamos y salio todo bien
        paymentDTO
                .getPayment()
                .setStatus(PaymentStatusEnum.APPROVED);

        paymentDTO.setResult(BusinessResultEnum.OK);
    }

    @Override
    public AuthsEnum getAuth() {
        return AuthsEnum.BISMA;
    }
}
