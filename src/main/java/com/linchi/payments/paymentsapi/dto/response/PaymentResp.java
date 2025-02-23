package com.linchi.payments.paymentsapi.dto.response;


import com.linchi.payments.paymentsapi.entitys.Payment;


import com.linchi.payments.paymentsapi.service.support.enums.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResp {

    private ResultEnum result;
    private String resultDescription;
    private Payment payment;

}
