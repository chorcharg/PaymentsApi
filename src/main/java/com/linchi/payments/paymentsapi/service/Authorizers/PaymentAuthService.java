package com.linchi.payments.paymentsapi.service.Authorizers;

import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;
import com.linchi.payments.paymentsapi.service.Authorizers.support.AuthsEnum;
import org.springframework.http.ResponseEntity;

//reglas de negocio del autorizador
//llamado al servicio
//mapeo de codigos de respuesta

public interface PaymentAuthService {

    public PaymentResp doPayment(PaymentReq paymentReq);

    public AuthsEnum getAuth();

    public default void reverse(){
        throw new UnsupportedOperationException();
    }

}
