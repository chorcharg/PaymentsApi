package com.linchi.payments.paymentsapi.entitys;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor
public class MethodBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

    @JoinColumns({
            @JoinColumn(name = "paymentId_commerceId", referencedColumnName = "commerceId"),
            @JoinColumn(name = "paymentId_payIntentionId", referencedColumnName = "payIntentionId")
    })
    private PaymentIntent paymentId;

}
