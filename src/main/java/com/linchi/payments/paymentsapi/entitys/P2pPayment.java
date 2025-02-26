package com.linchi.payments.paymentsapi.entitys;

import jakarta.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class P2pPayment extends MethodBase{

    private long senderId;
    private long receiverId;
    private String note;

}
