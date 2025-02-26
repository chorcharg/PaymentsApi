package com.linchi.payments.paymentsapi.entitys;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentIntent implements Serializable {

    private int commerceId;
    private int payIntentionId;

}
