package br.com.mkdelivery.payment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.mkdelivery.payment.api.domain.models.Payment;

public interface PaymentService {

	Payment save(Payment payment);

	Payment findByUuid(String uuid);

	Page<Payment> findByFilter(Payment filter, Pageable pageable);

	Payment chargeback(String uuid);

	Payment cancel(String uuid);

}
