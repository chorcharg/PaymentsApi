package com.linchi.payments.paymentsapi.service.managers.impl;

import com.linchi.payments.paymentsapi.dto.PaymentDTO;
import com.linchi.payments.paymentsapi.dto.request.P2pPaymentReq;
import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.entitys.MethodBase;
import com.linchi.payments.paymentsapi.entitys.P2pPayment;
import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;
import com.linchi.payments.paymentsapi.excpetions.FactoryException;
import com.linchi.payments.paymentsapi.repository.P2pRepository;
import com.linchi.payments.paymentsapi.service.Authorizers.PaymentAuthService;
import com.linchi.payments.paymentsapi.service.managers.PaymentManagerService;
import com.linchi.payments.paymentsapi.service.support.Mappers;
import com.linchi.payments.paymentsapi.service.support.enums.AuthsEnum;
import com.linchi.payments.paymentsapi.service.support.enums.ManagersEnum;
import com.linchi.payments.paymentsapi.service.support.enums.ResultEnum;
import com.linchi.payments.paymentsapi.service.support.factorys.AuthServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class P2pPaymentServiceImpl implements PaymentManagerService {


    private final P2pRepository p2pRepository;
    private final AuthServiceFactory authServiceFactory;

    @Autowired
    public P2pPaymentServiceImpl(P2pRepository p2pRepository, AuthServiceFactory authServiceFactory) {
        this.p2pRepository = p2pRepository;
        this.authServiceFactory = authServiceFactory;
    }

    @Override
    public void processPayment(PaymentDTO paymentDTO) {
        P2pPayment p2pPayment = (P2pPayment) paymentDTO.getMethod();

        PaymentAuthService paymentAuthService =
                this.authServiceFactory.getPaymentAuthService(
                        AuthsEnum.P2P
                );

        if(paymentAuthService == null) {
            paymentDTO.getPayment().setStatus(PaymentStatusEnum.ERROR);
            paymentDTO.setResult(ResultEnum.INVALID_AUTHORIZER);
            throw new FactoryException(
                    this.getClass().toString()

            );
        }

        paymentAuthService.doPayment(paymentDTO);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveTransaction(PaymentDTO paymentDTO) {
        P2pPayment transferPayment = (P2pPayment)paymentDTO.getMethod();
        this.p2pRepository.save(transferPayment);

    }

    @Override
    public ManagersEnum getManager() {
        return ManagersEnum.P2P;
    }

    @Override
    public MethodBase getMethod(PaymentReq paymentReq) {
        return Mappers
                .mapPayReqToP2pEntity(
                        (P2pPaymentReq) paymentReq
                );
    }
}
