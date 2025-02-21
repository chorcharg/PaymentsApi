package com.linchi.payments.paymentsapi.entitys;

import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GenerationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long cardPaymentId;

    @JoinColumns({
            @JoinColumn(name = "paymentId_commerceId", referencedColumnName = "commerceId"),
            @JoinColumn(name = "paymentId_payIntentionId", referencedColumnName = "payIntentionId")
    })
    private PaymentIntent paymentId;

    private String authorizer;

    private String cardNumber;


}
