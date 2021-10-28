package br.com.mkdelivery.payment.service;

import br.com.mkdelivery.payment.api.domain.models.Payment;

public interface PaymentService {

	Payment save(Payment payment);

}
