package com.linchi.payments.paymentsapi.entitys;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class TransferPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long transferPaymentId;


    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "paymentId_commerceId", referencedColumnName = "commerceId"),
            @JoinColumn(name = "paymentId_payIntentionId", referencedColumnName = "payIntentionId")
    })
    private Payment paymentId;

    long userId;

    private String bankCode;

    private String toAcct;


}
