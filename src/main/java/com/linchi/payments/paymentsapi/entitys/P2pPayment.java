package com.linchi.payments.paymentsapi.entitys;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class P2pPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long p2pPaymentId;


    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "paymentId_commerceId", referencedColumnName = "commerceId"),
            @JoinColumn(name = "paymentId_payIntentionId", referencedColumnName = "payIntentionId")
    })
    private Payment paymentId;

    private long senderId;
    private long receiverId;

    private String note;

}
