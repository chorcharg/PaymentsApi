package com.linchi.payments.paymentsapi.entitys;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CardPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long cardPaymentId;

    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "paymentId_commerceId", referencedColumnName = "commerceId"),
            @JoinColumn(name = "paymentId_payIntentionId", referencedColumnName = "payIntentionId")
    })
    private Payment paymentId;

    private String cardNumber;


}
