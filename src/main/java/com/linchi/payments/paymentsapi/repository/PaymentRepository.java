package com.linchi.payments.paymentsapi.repository;

import com.linchi.payments.paymentsapi.entitys.Payment;
import com.linchi.payments.paymentsapi.entitys.PaymentIntent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

   Optional< Payment> findByPaymentIntent(PaymentIntent paymentIntent);
}
