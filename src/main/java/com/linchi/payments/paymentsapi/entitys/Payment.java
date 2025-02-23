package com.linchi.payments.paymentsapi.entitys;

import com.linchi.payments.paymentsapi.service.support.enums.CurrencyEnum;
import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {

    @EmbeddedId
    private PaymentIntent paymentIntent;

    private Double amount;

    private Double localAmount;

    @Enumerated(EnumType.STRING)
    private CurrencyEnum currency;

    @Enumerated(EnumType.STRING)
    private PaymentStatusEnum status;

    @CreationTimestamp
    private Timestamp createdAt;

    private String description;

}
