package br.com.mkdelivery.payment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.mkdelivery.payment.api.domain.models.Payment;

public interface PaymentService {

	Payment save(Payment payment);

	Payment findById(String uuid);

	Page<Payment> findByFilter(Payment filter, Pageable pageable);

}
