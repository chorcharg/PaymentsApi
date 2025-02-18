package com.linchi.payments.paymentsapi.entitys;

import jakarta.persistence.*;
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

    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "paymentId_commerceId", referencedColumnName = "commerceId"),
            @JoinColumn(name = "paymentId_payIntentionId", referencedColumnName = "payIntentionId")
    })

    private Payment paymentId;
    private String authorizer;
    private String cardNumber;


}
