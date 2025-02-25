package com.linchi.payments.paymentsapi.service.payments;


import com.linchi.payments.paymentsapi.dto.PaymentDTO;
import com.linchi.payments.paymentsapi.dto.request.PaymentListReq;
import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.entitys.Payment;
import com.linchi.payments.paymentsapi.service.managers.PaymentManagerService;
import com.linchi.payments.paymentsapi.service.support.enums.BusinessResultEnum;
import com.linchi.payments.paymentsapi.service.support.enums.ManagersEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface PaymentSupport {

    void startPayment(PaymentDTO paymentDTO, PaymentManagerService payManager);

    void updatePaymentDTO(PaymentDTO paymentDTO, BusinessResultEnum result );
    void updatePayment(PaymentDTO paymentDTO);

    PaymentDTO getPaymentDTO(PaymentReq paymentReq, ManagersEnum method);
    Pageable buildPageConfig(PaymentListReq paymentListReq);
    Specification<Payment> buildSpecs(PaymentListReq paymentListReq);



}
