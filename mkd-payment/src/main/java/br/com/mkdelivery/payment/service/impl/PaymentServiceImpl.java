package br.com.mkdelivery.payment.service.impl;

import org.springframework.stereotype.Service;

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
	
	@Override
	public Payment save(Payment payment) {
		
		CreditCardValidator cardValidator = new CreditCardValidator();
		if(payment instanceof PaymentCreditCard 
				&& !cardValidator.isValidCreditCard(payment)) {
			throw new BusinessException("Invalid credit card.");
		}
		
		payment.generateUuid();
		return repository.save(payment);
	}

}
