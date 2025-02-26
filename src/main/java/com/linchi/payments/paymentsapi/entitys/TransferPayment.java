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
public class TransferPayment extends MethodBase{

    long userId;
    private String bankCode;
    private String toAcct;

}
