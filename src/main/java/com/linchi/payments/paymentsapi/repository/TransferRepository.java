package com.linchi.payments.paymentsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linchi.payments.paymentsapi.entitys.TransferPayment;

@Repository
public interface TransferRepository extends JpaRepository<TransferPayment, Long> {}
