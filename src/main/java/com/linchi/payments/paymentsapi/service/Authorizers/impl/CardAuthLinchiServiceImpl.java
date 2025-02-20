package com.linchi.payments.paymentsapi.service.Authorizers.impl;

import com.linchi.payments.paymentsapi.dto.request.CardPaymentReq;
import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;
import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;
import com.linchi.payments.paymentsapi.excpetions.BussinesException;
import com.linchi.payments.paymentsapi.excpetions.ExceptionEnum;
import com.linchi.payments.paymentsapi.service.Authorizers.PaymentAuthService;
import com.linchi.payments.paymentsapi.service.support.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class CardAuthLinchiServiceImpl implements PaymentAuthService {

    public CardAuthLinchiServiceImpl(){

    }

    @Override
    public ResponseEntity<PaymentResp> doPayment(PaymentReq paymentReq) {

        CardPaymentReq cardPaymentReq = (CardPaymentReq)paymentReq;

        //ACA IRIAN REGLAS DE NEGOCIO DEL PROVEEDOR, ademas del mapero de los codigos de repsuesta del proveedor a codigos propios de la api......

        //TO_DO: hacer ENUM para poder mapear los codigos


        // esto se reemplazaria con la llamada al/los servicios externos, se mockea aca para no complicar con mas clases
        //para test, importes mayores a 10.000 se rechazan
        if(cardPaymentReq.getAmount() > 10000){
            throw new BussinesException(ExceptionEnum.INSUFFICIENT_BALANCE, paymentReq);

        }

        //para test, tarjeta = 123 se rechaza
        if(cardPaymentReq.getCardNumber().equals("123")){

            throw new BussinesException(ExceptionEnum.INVALID_CARD, paymentReq);
        }

        // OK
        return new ResponseEntity<PaymentResp>(
                Mappers.mapPayReqToPayResp(
                        paymentReq,
                        PaymentStatusEnum.FINISHED,
                        "Operacion reazliada"),
                HttpStatus.OK
        );
    }


}
