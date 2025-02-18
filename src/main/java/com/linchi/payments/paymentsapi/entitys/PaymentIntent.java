package com.linchi.payments.paymentsapi.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

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
