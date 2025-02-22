package com.linchi.payments.paymentsapi.repository;

import com.linchi.payments.paymentsapi.entitys.Payment;
import com.linchi.payments.paymentsapi.entitys.PaymentIntent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

   Optional< Payment> findByPaymentIntent(PaymentIntent paymentIntent);
   Page<Payment> findByPaymentIntent_CommerceId(int commerceId, Pageable pageable);
}
