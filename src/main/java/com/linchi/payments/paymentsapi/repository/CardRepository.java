package com.linchi.payments.paymentsapi.repository;

import com.linchi.payments.paymentsapi.entitys.CardPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<CardPayment, Long> {

}
