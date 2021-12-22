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
		assertThat(paymentSaved.getStatus()).isEqualTo(PaymentStatus.RECEIVED);
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
	void findPaymentById() {
		
		PaymentSlip paymentSlip = UtilPayment.paymentSlip();
		
		when(repository.findByUuid(anyString())).thenReturn(Optional.of(paymentSlip));

		Payment paymentFounded = service.findByUuid(paymentSlip.getUuid());
		
		assertThat(paymentFounded).isNotNull();	
		
	}

	@Test
	@DisplayName("Should throw exception when not found a payment by uuid")
	void notFoundPaymentById() {
		
		String id = "a0d7a0bf-ac09-4af6-b56f-13c1277a6b52";
		String message = "Payment not found. id: " + id;
		
		when(repository.findByUuid(anyString())).thenReturn(Optional.empty());
		
		Throwable throwable = catchThrowable(() -> service.findByUuid(id));
		
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
	
	@Test
	@DisplayName("Should reverse a payment")
	void chargebackPayment() {
		
		String uuid = "a0d7a0bf-ac09-4af6-b56f-13c1277a6b52";
		
		PaymentSlip paymentReturn = UtilPayment.paymentSlip();
		paymentReturn.setStatus(PaymentStatus.REVERSED);
		
		PaymentSlip paymentStatusApproved = paymentReturn;
		paymentStatusApproved.setStatus(PaymentStatus.APPROVED);
		
		when(repository.save(Mockito.any())).thenReturn(paymentReturn);
		when(repository.findByUuid(Mockito.anyString())).thenReturn(Optional.of(paymentStatusApproved));
		
		Payment payment = service.chargeback(uuid);
		
		assertThat(payment).isNotNull();
		assertThat(payment.getStatus()).isEqualTo(PaymentStatus.REVERSED);
	}
	
	@Test
	@DisplayName("Should throw a invalid reverse a payment")
	void invalidChargebackPayment() {
		
		PaymentSlip paymentReturn = UtilPayment.paymentSlip();
		
		String uuid = "a0d7a0bf-ac09-4af6-b56f-13c1277a6b52";
		String message = "The payment can't be reversed";
		
		when(repository.save(Mockito.any())).thenReturn(paymentReturn);
		when(repository.findByUuid(Mockito.anyString())).thenReturn(Optional.of(paymentReturn));
		
		Throwable throwable = catchThrowable(() -> service.chargeback(uuid));
		
		assertThat(throwable)
			.isInstanceOf(BusinessException.class)
			.hasMessage(message);
	}
	
}
