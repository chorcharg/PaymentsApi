package com.linchi.payments.paymentsapi.service.providers;

public interface PaymentProviderService {

    public void doPayment();

    public default void callBack(){

        throw new UnsupportedOperationException();
    }

    public default void reverse(){

        throw new UnsupportedOperationException();
    }


}
