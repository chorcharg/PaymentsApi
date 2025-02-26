package com.linchi.payments.paymentsapi.service.managers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.linchi.payments.paymentsapi.dto.PaymentDTO;
import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.dto.request.TransferPaymentReq;
import com.linchi.payments.paymentsapi.entitys.MethodBase;
import com.linchi.payments.paymentsapi.entitys.TransferPayment;
import com.linchi.payments.paymentsapi.excpetions.FactoryException;
import com.linchi.payments.paymentsapi.repository.TransferRepository;
import com.linchi.payments.paymentsapi.service.authorizers.PaymentAuthService;
import com.linchi.payments.paymentsapi.service.managers.PaymentManagerService;
import com.linchi.payments.paymentsapi.service.payments.PaymentSupport;
import com.linchi.payments.paymentsapi.service.support.Mappers;
import com.linchi.payments.paymentsapi.service.support.enums.AuthsEnum;
import com.linchi.payments.paymentsapi.service.support.enums.ManagersEnum;
import com.linchi.payments.paymentsapi.service.support.enums.BusinessResultEnum;
import com.linchi.payments.paymentsapi.service.support.factorys.AuthServiceFactory;


@Service
public class TransferPaymentServiceImpl implements PaymentManagerService {

    private final PaymentSupport paymentSupport;
    private final AuthServiceFactory authServiceFactory;
    private final TransferRepository transferRepository;

    @Autowired
    public TransferPaymentServiceImpl(AuthServiceFactory authServiceFactory, TransferRepository transferRepository, PaymentSupport paymentSupport) {
        this.paymentSupport = paymentSupport;
        this.authServiceFactory = authServiceFactory;
        this.transferRepository = transferRepository;
    }

    @Override
    public void processPayment(PaymentDTO paymentDTO) {

        TransferPayment transferPayment = (TransferPayment) paymentDTO.getMethod();

        PaymentAuthService paymentAuthService =
                this.authServiceFactory.getPaymentAuthService(
                        AuthsEnum.TRANSFER
                );

            if(paymentAuthService == null) {
                this.paymentSupport
                        .updatePaymentDTO(
                                paymentDTO,
                                BusinessResultEnum.INVALID_AUTHORIZER
                        );

                this.paymentSupport.updatePayment(paymentDTO);
                throw new FactoryException(
                        this.getClass().toString()

                );
            }

        paymentAuthService.doPayment(paymentDTO);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveTransaction(PaymentDTO paymentDTO) {

        TransferPayment transferPayment = (TransferPayment) paymentDTO.getMethod();
        this.transferRepository.save(transferPayment);

    }

    @Override
    public ManagersEnum getManager() {

        return ManagersEnum.TRANSFER;
    }

    @Override

    public MethodBase getMethod(PaymentReq paymentReq) {
        return Mappers
                .mapPayReqToTransferEntity(
                        (TransferPaymentReq)paymentReq
                );
    }
}
