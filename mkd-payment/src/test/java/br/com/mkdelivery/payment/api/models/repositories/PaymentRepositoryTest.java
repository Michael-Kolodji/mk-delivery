package br.com.mkdelivery.payment.api.models.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.mkdelivery.payment.api.domain.enums.PaymentStatus;
import br.com.mkdelivery.payment.api.domain.models.Payment;
import br.com.mkdelivery.payment.api.domain.models.PaymentSlip;
import br.com.mkdelivery.payment.util.UtilPayment;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
class PaymentRepositoryTest {

	@Autowired
	PaymentRepository repository;
	
	@Autowired
	TestEntityManager manager;
	
	@Test
	@DisplayName("Should create a payment")
	void savePayment() {
		Payment paymentSaved = repository.save(UtilPayment.paymentSlip());
		
		assertThat(paymentSaved).isNotNull();
		assertThat(paymentSaved.getId()).isNotNull();
	}
	
	@Test
	@DisplayName("Should throw exception when create a invalid payment")
	void saveInvalidPayment() {
		Throwable throwable = catchThrowable(() -> repository.save(new PaymentSlip()));
		
		assertThat(throwable)
			.isInstanceOf(ConstraintViolationException.class);
	}
	
	@Test
	@DisplayName("Should found a payment by uuid")
	void findPaymentById() throws Exception {
		PaymentSlip paymentSaved = manager.persist(UtilPayment.paymentSlip());
		
		Optional<Payment> paymentFounded = repository.findByUuid(paymentSaved.getUuid());
		
		assertThat(paymentFounded).isNotNull();
		assertThat(paymentFounded.get().getUuid()).isEqualTo(paymentSaved.getUuid());
	}
	
	@Test
	@DisplayName("Should found payments by filter")
	void findByPaymentFilter() {
		
		var payment = manager.persist(UtilPayment.paymentSlip());
		
		PageRequest pageRequest = PageRequest.of(0, 10);
		
		Example<Payment> example = Example.of(payment, 
				ExampleMatcher
					.matching()
					.withIgnoreCase()
					.withIgnoreNullValues()
					.withStringMatcher(StringMatcher.CONTAINING));
		
		Page<Payment> result = repository.findAll(example, pageRequest);
		
		assertThat(result.getContent()).isNotEmpty();
		assertThat(result.getTotalElements()).isEqualTo(1);
		assertThat(result.getPageable().getPageNumber()).isZero();
		assertThat(result.getPageable().getPageSize()).isEqualTo(10);
		
	}
	
	@Test
	@DisplayName("Should reverse a payment")
	void chargebackPayment() {
		
		PaymentSlip paymentToSave = UtilPayment.paymentSlip();
		paymentToSave.setStatus(PaymentStatus.REVERSED);
		
		var payment = manager.persist(paymentToSave);
		
		assertThat(payment).isNotNull();
		assertThat(payment.getStatus()).isEqualTo(PaymentStatus.REVERSED);
		
	}
	
	@Test
	@DisplayName("Should cancel a payment")
	void cancelPayment() {
		
		PaymentSlip paymentToSave = UtilPayment.paymentSlip();
		paymentToSave.setStatus(PaymentStatus.CANCELED);
		
		var payment = manager.persist(paymentToSave);
		
		assertThat(payment).isNotNull();
		assertThat(payment.getStatus()).isEqualTo(PaymentStatus.CANCELED);
	}
	
}
