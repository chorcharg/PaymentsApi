package com.linchi.payments.paymentsapi.service.support;

public enum MonExt {

    ARS(1),
    EUR(1300.08),
    USD(1200.0),
    UYP(300.07);

    private final double rate;


    MonExt(double rate) {
        this.rate = rate;
    }

    public double rateToArs() {
        return rate;
    }

}
