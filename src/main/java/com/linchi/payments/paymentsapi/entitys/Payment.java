package com.linchi.payments.paymentsapi.entitys;
import com.linchi.payments.paymentsapi.entitys.enums.CurrencyEnum;
import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
public class Payment {


    @EmbeddedId
    private PaymentIntent paymentIntent;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private CurrencyEnum currency;

    @Enumerated(EnumType.STRING)
    private PaymentStatusEnum status;

    private Date createdAt;


}
