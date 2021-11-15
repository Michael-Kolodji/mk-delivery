package br.com.mkdelivery.payment.api.models.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.mkdelivery.payment.api.domain.models.Payment;
import br.com.mkdelivery.payment.api.domain.models.PaymentSlip;
import br.com.mkdelivery.payment.util.UtilPayment;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
class PaymentRepositoryTest {

	@Autowired
	PaymentRepository repository;
	
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
	
}
