package com.linchi.payments.paymentsapi.repository;


import com.linchi.payments.paymentsapi.entitys.TransferPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends JpaRepository<TransferPayment, Long> {
}
