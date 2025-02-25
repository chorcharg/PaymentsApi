package com.linchi.payments.paymentsapi.service.managers.impl;

import com.linchi.payments.paymentsapi.dto.PaymentDTO;
import com.linchi.payments.paymentsapi.dto.request.CardPaymentReq;
import com.linchi.payments.paymentsapi.dto.request.PaymentReq;
import com.linchi.payments.paymentsapi.entitys.CardPayment;
import com.linchi.payments.paymentsapi.entitys.MethodBase;
import com.linchi.payments.paymentsapi.excpetions.FactoryException;
import com.linchi.payments.paymentsapi.repository.CardRepository;
import com.linchi.payments.paymentsapi.service.authorizers.PaymentAuthService;
import com.linchi.payments.paymentsapi.service.managers.PaymentManagerService;
import com.linchi.payments.paymentsapi.service.payments.PaymentSupport;
import com.linchi.payments.paymentsapi.service.support.*;

import com.linchi.payments.paymentsapi.service.support.enums.AuthsEnum;
import com.linchi.payments.paymentsapi.service.support.enums.BusinessResultEnum;
import com.linchi.payments.paymentsapi.service.support.enums.ManagersEnum;
import com.linchi.payments.paymentsapi.service.support.factorys.AuthServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CardPaymentServiceImpl implements PaymentManagerService {

    private final PaymentSupport paymentSupport;
    private final CardRepository cardRepository;
    private final AuthServiceFactory authServiceFactory;


    @Autowired
    public CardPaymentServiceImpl(CardRepository cardRepository, AuthServiceFactory authServiceFactory, PaymentSupport paymentSupport) {
        this.paymentSupport = paymentSupport;
        this.cardRepository = cardRepository;
        this.authServiceFactory = authServiceFactory;
    }

    @Override
    public void processPayment(PaymentDTO paymentDTO) throws FactoryException {



        CardPayment cardPayment = (CardPayment)paymentDTO.getMethod();

        //reglas.....

        //determinamos el servicio de pago

        PaymentAuthService paymentAuthService =
                this.authServiceFactory
                .getPaymentAuthService(
                        AuthsEnum
                        .fromName(cardPayment.getAuthorizer())
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

        try {
            paymentAuthService.doPayment(paymentDTO);
        } catch(Exception ignored) {

        }


    }

    public MethodBase getMethod(PaymentReq paymentReq ) {
        return Mappers
                .mapPayReqToCardEntity(
                        (CardPaymentReq) paymentReq
                );
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveTransaction(PaymentDTO paymentDTO) {

        CardPayment cardPayment = (CardPayment)paymentDTO.getMethod();
        this.cardRepository.save(cardPayment);

    }

    @Override
    public ManagersEnum getManager() {
        return ManagersEnum.DEBIT_CARD;
    }

}
