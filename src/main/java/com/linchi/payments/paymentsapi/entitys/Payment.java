package com.linchi.payments.paymentsapi.entitys;

import java.sql.Timestamp;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;

import com.linchi.payments.paymentsapi.service.support.enums.CurrencyEnum;
import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;
import com.linchi.payments.paymentsapi.service.support.enums.ManagersEnum;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {

    @Id
    @EmbeddedId
    private PaymentIntent paymentIntent;

    @Enumerated(EnumType.STRING)
    private CurrencyEnum currency;

    @Enumerated(EnumType.STRING)
    private PaymentStatusEnum status;

    @Enumerated(EnumType.STRING)
    private ManagersEnum method;

    @CreationTimestamp
    private Timestamp createdAt;

    private String description;
    private Double amount;
    private Double localAmount;

}
