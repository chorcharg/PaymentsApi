package com.linchi.payments.paymentsapi.service.support.enums;

import lombok.Getter;

@Getter
public enum CurrencyEnum {

    ARS(1),
    EUR(1300.08),
    USD(1200.0),
    UYP(300.07)

    ;

    private final double rate;

    CurrencyEnum(double rate) {
        this.rate = rate;
    }

    public double rateToArs(Double amount)
    {
        return rate * amount;
    }

}
