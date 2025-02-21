package com.linchi.payments.paymentsapi.entitys;

import jakarta.persistence.Embeddable;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentIntent implements Serializable {

    private int commerceId;
    private int payIntentionId;

}
