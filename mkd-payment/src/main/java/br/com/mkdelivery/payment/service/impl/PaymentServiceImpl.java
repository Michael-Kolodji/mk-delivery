package br.com.mkdelivery.payment.service.impl;

import org.springframework.stereotype.Service;

import br.com.mkdelivery.payment.api.domain.models.Payment;
import br.com.mkdelivery.payment.api.models.repositories.PaymentRepository;
import br.com.mkdelivery.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private final PaymentRepository repository;
	
	@Override
	public Payment save(Payment payment) {
		payment.generateUuid();
		return repository.save(payment);
	}

}
