package com.linchi.payments.paymentsapi.dto.request;




import com.linchi.payments.paymentsapi.entitys.enums.PaymentStatusEnum;
import com.linchi.payments.paymentsapi.service.support.enums.ManagersEnum;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Sort.Direction;


@Data
@AllArgsConstructor

public class PaymentListReq {

    @Positive
    @NotNull
    private int commerceId;

    @Positive
    @NotNull
    private Integer size;

    @PositiveOrZero
    @NotNull
    private Integer page;

    private PaymentStatusEnum status;
    private Double minAmount;
    private Double maxAmount;
    private ManagersEnum method;

    private String sortBy;


    private Direction sortDirection;
}
