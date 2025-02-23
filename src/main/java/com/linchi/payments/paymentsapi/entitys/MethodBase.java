package com.linchi.payments.paymentsapi.entitys;


import jakarta.persistence.*;
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

    public MethodBase(long id, PaymentIntent paymentId) {
        this.Id = id;
        this.paymentId = paymentId;
    }
}
