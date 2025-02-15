package com.linchi.payments.paymentsapi.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class PaymentIntent implements Serializable {

    private int commerceId;
    private String payIntentionId;

}
