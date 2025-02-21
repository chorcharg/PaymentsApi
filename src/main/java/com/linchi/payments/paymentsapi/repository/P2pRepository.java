package com.linchi.payments.paymentsapi.repository;

import com.linchi.payments.paymentsapi.entitys.P2pPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface P2pRepository extends JpaRepository <P2pPayment, Long> {

}
