package com.linchi.payments.paymentsapi.service.Authorizers.impl;

import com.linchi.payments.paymentsapi.dto.PaymentDTO;
import com.linchi.payments.paymentsapi.entitys.TransferPayment;
import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;
import com.linchi.payments.paymentsapi.excpetions.BusinessException;
import com.linchi.payments.paymentsapi.service.Authorizers.PaymentAuthService;
import com.linchi.payments.paymentsapi.service.support.enums.AuthsEnum;
import com.linchi.payments.paymentsapi.service.support.enums.ResultEnum;
import org.springframework.stereotype.Service;

@Service
public class TrasnferAuthLinchiServiceImpl implements PaymentAuthService {
    @Override
    public void doPayment(PaymentDTO paymentDTO) {

        TransferPayment transferPayment = (TransferPayment) paymentDTO.getMethod();

        if(transferPayment.getBankCode().equalsIgnoreCase("PARIBAS"))
        {
            paymentDTO.getPayment().setStatus(PaymentStatusEnum.FAILED);
            paymentDTO.setResult(ResultEnum.INVALID_BANK_CODE);
            paymentDTO.getPayment().setDescription(ResultEnum.INVALID_BANK_CODE.getDescription());
            throw new BusinessException(ResultEnum.INVALID_BANK_CODE);
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
        paymentDTO.setResult(ResultEnum.OK);
    }

    @Override
    public AuthsEnum getAuth() {
        return AuthsEnum.TRANSFER;
    }
}
