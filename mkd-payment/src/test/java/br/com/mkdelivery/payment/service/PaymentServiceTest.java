package br.com.mkdelivery.payment.service;

import static org.assertj.core.api.Assertions.assertThat;

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
import br.com.mkdelivery.payment.service.impl.PaymentServiceImpl;
import br.com.mkdelivery.payment.util.UtilPayment;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class PaymentServiceTest {

	private PaymentService service;
	
	@MockBean
	private PaymentRepository repository;
	
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
	
	
}
