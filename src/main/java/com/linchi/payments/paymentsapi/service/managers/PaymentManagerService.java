package com.linchi.payments.paymentsapi.service.managers;

import com.linchi.payments.paymentsapi.dto.PaymentDTO;
import com.linchi.payments.paymentsapi.dto.request.PaymentReq;


import com.linchi.payments.paymentsapi.entitys.MethodBase;
import com.linchi.payments.paymentsapi.service.support.enums.ManagersEnum;

//reglas de negocio por tipo de pago.
//elije proveedor y pide auotrizacion

public interface PaymentManagerService {

    void processPayment(PaymentDTO paymentDTO);

    void saveTransaction(PaymentDTO paymentDTO);

    ManagersEnum getManager();

    MethodBase getMethod(PaymentReq paymentReq);

}
