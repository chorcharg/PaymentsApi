package com.linchi.payments.paymentsapi.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.linchi.payments.paymentsapi.entitys.Payment;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentListResp {

    private int size;
    private int page;
    private List<Payment> payments;

}
