package br.com.mkdelivery.payment.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.mkdelivery.payment.api.domain.models.Payment;
import br.com.mkdelivery.payment.api.models.repositories.PaymentRepository;
import br.com.mkdelivery.payment.exception.BusinessException;
import br.com.mkdelivery.payment.service.impl.PaymentServiceImpl;
import br.com.mkdelivery.payment.util.UtilPayment;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class PaymentServiceTest {

	PaymentService service;
	
	@MockBean
	PaymentRepository repository;
	
	@BeforeEach
	void setup() {
		service = new PaymentServiceImpl(repository); 
	}
	
	@Test
	@DisplayName("Should create a payment")
	void savePayment() {
		
		Payment payment = UtilPayment.paymentSlip();
		payment.setId(1l);
		
		Mockito.when(repository.save(Mockito.any(Payment.class))).thenReturn(payment );
		
		Payment paymentSaved = service.save(UtilPayment.paymentSlip());
		
		assertThat(paymentSaved).isNotNull();
		assertThat(paymentSaved.getId()).isNotNull();
	}
	
	@Test
	@DisplayName("Should throw exception when create a payment with invalid credit card")
	void validCreditCard() {
		var paymentCard = UtilPayment.paymentCreditCard();
		paymentCard.setCardNumber("45566546464665645");
		
		Throwable throwable = catchThrowable(() -> service.save(paymentCard));
		
		assertThat(throwable)
			.isInstanceOf(BusinessException.class)
			.hasMessage("Invalid credit card.");
	}

}
