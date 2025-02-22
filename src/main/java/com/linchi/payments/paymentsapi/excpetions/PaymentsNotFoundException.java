package com.linchi.payments.paymentsapi.excpetions;

import com.linchi.payments.paymentsapi.dto.request.PaymentStatusReq;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PaymentsNotFoundException extends RuntimeException {

    PaymentStatusReq paymentStatusReq;

    public PaymentsNotFoundException(PaymentStatusReq paymentStatusReq) {

        this.paymentStatusReq = paymentStatusReq;

    }


}
