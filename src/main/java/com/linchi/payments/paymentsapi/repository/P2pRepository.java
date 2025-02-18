package com.linchi.payments.paymentsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.linchi.payments.paymentsapi.entitys.TransferPayment;

public interface P2pRepository extends JpaRepository <TransferPayment, Long> {
}
