package br.com.mkdelivery.payment.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.mkdelivery.payment.api.domain.enums.PaymentStatus;
import br.com.mkdelivery.payment.api.domain.models.Payment;
import br.com.mkdelivery.payment.api.domain.models.PaymentSlip;
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
		assertThat(paymentSaved.getStatus()).isEqualTo(PaymentStatus.RECEBIDO);
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

	@Test
	@DisplayName("Should found a payment by uuid")
	void findPaymentById() throws Exception {
		
		PaymentSlip paymentSlip = UtilPayment.paymentSlip();
		
		when(repository.findByUuid(anyString())).thenReturn(Optional.of(paymentSlip));

		Payment paymentFounded = service.findById(paymentSlip.getUuid());
		
		assertThat(paymentFounded).isNotNull();	
		
	}

	@Test
	@DisplayName("Should throw exception when not found a payment by uuid")
	void notFoundPaymentById() throws Exception {
		
		String id = "a0d7a0bf-ac09-4af6-b56f-13c1277a6b52";
		String message = "Payment not found. id: " + id;
		
		when(repository.findByUuid(anyString())).thenReturn(Optional.empty());
		
		Throwable throwable = catchThrowable(() -> service.findById(id));
		
			assertThat(throwable)
				.isInstanceOf(EntityNotFoundException.class)
				.hasMessage(message);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@DisplayName("Should found payments by filter")
	void findByPaymentFilter() {
		
		var paymentSlip = UtilPayment.paymentSlip();
		
		PageRequest pageRequest = PageRequest.of(0, 10);
		
		
		List<Payment> payments = Arrays.asList(paymentSlip);
		PageImpl<Payment> page = new PageImpl<>(payments, pageRequest, 1);
		
		when(repository.findAll(Mockito.any(Example.class), Mockito.any(PageRequest.class))).thenReturn(page);
		
		Page<Payment> paymentsFounded = service.findByFilter(paymentSlip, pageRequest);
		
		assertThat(paymentsFounded.getTotalElements()).isEqualTo(1);
		assertThat(paymentsFounded.getContent()).isEqualTo(payments);
		assertThat(paymentsFounded.getPageable().getPageNumber()).isZero();
		assertThat(paymentsFounded.getPageable().getPageSize()).isEqualTo(10);
	}
	
}
