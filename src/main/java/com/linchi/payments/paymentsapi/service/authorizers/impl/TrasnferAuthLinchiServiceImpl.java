package com.linchi.payments.paymentsapi.service.authorizers.impl;

import com.linchi.payments.paymentsapi.dto.PaymentDTO;
import com.linchi.payments.paymentsapi.entitys.TransferPayment;
import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;
import com.linchi.payments.paymentsapi.excpetions.BusinessException;
import com.linchi.payments.paymentsapi.service.authorizers.PaymentAuthService;
import com.linchi.payments.paymentsapi.service.payments.PaymentSupport;
import com.linchi.payments.paymentsapi.service.support.enums.AuthsEnum;
import com.linchi.payments.paymentsapi.service.support.enums.BusinessResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrasnferAuthLinchiServiceImpl implements PaymentAuthService {

    PaymentSupport paymentSupport;

    @Autowired
    public TrasnferAuthLinchiServiceImpl(PaymentSupport paymentSupport) {
        this.paymentSupport = paymentSupport;
    }


    @Override
    public void doPayment(PaymentDTO paymentDTO) {

        TransferPayment transferPayment = (TransferPayment) paymentDTO.getMethod();

        if(transferPayment.getBankCode().equalsIgnoreCase("PARIBAS"))
        {
            this.paymentSupport
                    .updatePaymentDTO(
                            paymentDTO,
                            BusinessResultEnum.INVALID_BANK_CODE
                    );

            this.paymentSupport.updatePayment(paymentDTO);
            throw new BusinessException(BusinessResultEnum.INVALID_BANK_CODE);
        }

/*        TransferPaymentReq transferPaymentReq = (TransferPaymentReq) paymentReq;

        if(transferPaymentReq.getBankCode().equals("PARIBAS")) {
            return Mappers.mapPayReqToPayResp(
                    paymentReq,
                    PaymentStatusEnum.ERROR,
                    BussinesResultEnum.INVALID_BANK_CODE.getDescription()
            );
        }


        return Mappers.mapPayReqToPayResp(
                paymentReq,
                PaymentStatusEnum.APPROVED,
                BussinesResultEnum.OK.getDescription()
        );*/
        paymentDTO.getPayment().setStatus(PaymentStatusEnum.APPROVED);
        paymentDTO.setResult(BusinessResultEnum.OK);
    }

    @Override
    public AuthsEnum getAuth() {
        return AuthsEnum.TRANSFER;
    }
}
