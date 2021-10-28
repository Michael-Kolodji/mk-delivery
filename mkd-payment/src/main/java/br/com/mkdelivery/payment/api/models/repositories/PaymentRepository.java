package br.com.mkdelivery.payment.api.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mkdelivery.payment.api.domain.models.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
