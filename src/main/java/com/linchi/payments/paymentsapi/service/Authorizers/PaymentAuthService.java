package com.linchi.payments.paymentsapi.service.Authorizers;

import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;
import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;
import org.springframework.http.ResponseEntity;

public interface PaymentAuthService {

    public ResponseEntity<PaymentResp> doPayment(PaymentReq paymentReq);

    public default void callBack(){

        throw new UnsupportedOperationException();
    }

    public default void reverse(){

        throw new UnsupportedOperationException();
    }



}
