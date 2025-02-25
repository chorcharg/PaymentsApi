package com.linchi.payments.paymentsapi.service.authorizers;

import com.linchi.payments.paymentsapi.dto.PaymentDTO;
import com.linchi.payments.paymentsapi.service.support.enums.AuthsEnum;

//reglas de negocio del autorizador
//llamado al servicio
//mapeo de codigos de respuesta

public interface PaymentAuthService {

    void doPayment(PaymentDTO paymentDTO);

    AuthsEnum getAuth();

    default void reverse(){
        throw new UnsupportedOperationException();
    }

}
