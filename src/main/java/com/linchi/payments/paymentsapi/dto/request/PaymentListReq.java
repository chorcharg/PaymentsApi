package com.linchi.payments.paymentsapi.dto.request;




import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor

public class PaymentListReq {
    private int commerceId;
    private int size;
    private int page;

}
