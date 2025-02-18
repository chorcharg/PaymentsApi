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
