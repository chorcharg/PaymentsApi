package com.linchi.payments.paymentsapi.repository;

import com.linchi.payments.paymentsapi.entitys.CardPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<CardPayment, Long> {

}
