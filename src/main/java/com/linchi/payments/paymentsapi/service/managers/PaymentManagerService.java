package com.linchi.payments.paymentsapi.service.managers;

import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.response.PaymentResp;

import com.linchi.payments.paymentsapi.service.support.enums.ManagersEnum;

//reglas de negocio por tipo de pago.
//elije proveedor y pide auotrizacion

public interface PaymentManagerService {

    public PaymentResp processPayment(PaymentReq paymentReq);

    public void saveTransaction(PaymentReq paymentReq);

    public ManagersEnum getManager();

}
