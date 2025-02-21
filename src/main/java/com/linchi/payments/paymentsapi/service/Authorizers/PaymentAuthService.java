package com.linchi.payments.paymentsapi.service.Authorizers;

import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;
import org.springframework.http.ResponseEntity;

//reglas de negocio del autorizador
//llamado al servicio
//mapeo de codigos de respuesta

public interface PaymentAuthService {

    public ResponseEntity<PaymentResp> doPayment(PaymentReq paymentReq);

    public default void reverse(){
        throw new UnsupportedOperationException();
    }

}
