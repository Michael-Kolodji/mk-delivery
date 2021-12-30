package br.com.mkdelivery.payment.service.impl;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.mkdelivery.payment.api.domain.enums.PaymentStatus;
import br.com.mkdelivery.payment.api.domain.models.Payment;
import br.com.mkdelivery.payment.api.domain.models.PaymentCreditCard;
import br.com.mkdelivery.payment.api.models.repositories.PaymentRepository;
import br.com.mkdelivery.payment.exception.BusinessException;
import br.com.mkdelivery.payment.service.PaymentService;
import br.com.mkdelivery.payment.service.validators.CreditCardValidator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private final PaymentRepository repository;
	private final ProcessPayment processPayment;
	
	@Override
	public Payment save(Payment payment) {
		
		if(payment instanceof PaymentCreditCard 
				&& !new CreditCardValidator().isValidCreditCard(payment)) {
			throw new BusinessException("Invalid credit card.");
		}
		
		payment.generateUuid();
		payment.setStatus(PaymentStatus.RECEIVED);
		
		Payment paymentSaved = repository.save(payment);
		
		processPayment.sendPayment(payment);
		
		return paymentSaved;
	}

	@Override
	public Payment findByUuid(String uuid) {
		return repository.findByUuid(uuid)
				.orElseThrow(() -> 
					new EntityNotFoundException("Payment not found. id: " + uuid));
	}

	@Override
	public Page<Payment> findByFilter(Payment filter, Pageable pageable) {
		var example = Example.of(filter, 
							ExampleMatcher
								.matching()
								.withIgnoreCase()
								.withIgnoreNullValues()
								.withStringMatcher(StringMatcher.CONTAINING));
		return repository.findAll(example, pageable);
	}

	@Override
	public Payment chargeback(String uuid) {
		Payment payment = findByUuid(uuid);
		
		if(!payment.getStatus().equals(PaymentStatus.APPROVED)) {
			throw new BusinessException("The payment can't be reversed");
		}
		
		payment.setStatus(PaymentStatus.REVERSED);
		
		return repository.save(payment);
	}

	@Override
	public Payment cancel(String uuid) {
		Payment payment = findByUuid(uuid);

		if (!payment.getStatus().equals(PaymentStatus.RECEIVED)) {
			throw new BusinessException("The payment can't be canceled");
		}
		
		payment.setStatus(PaymentStatus.CANCELED);
		
		return repository.save(payment);
	}

}
