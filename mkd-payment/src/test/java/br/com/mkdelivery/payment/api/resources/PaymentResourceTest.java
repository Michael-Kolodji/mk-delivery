package br.com.mkdelivery.payment.api.resources;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import javax.persistence.EntityNotFoundException;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.mkdelivery.payment.api.domain.enums.PaymentStatus;
import br.com.mkdelivery.payment.api.domain.enums.PaymentType;
import br.com.mkdelivery.payment.api.domain.models.Payment;
import br.com.mkdelivery.payment.api.domain.models.PaymentSlip;
import br.com.mkdelivery.payment.api.dto.PaymentCreditCardDTO;
import br.com.mkdelivery.payment.api.dto.PaymentDTO;
import br.com.mkdelivery.payment.api.dto.PaymentSlipDTO;
import br.com.mkdelivery.payment.api.filter.PaymentFilter;
import br.com.mkdelivery.payment.exception.BusinessException;
import br.com.mkdelivery.payment.service.PaymentService;
import br.com.mkdelivery.payment.util.UtilPayment;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WebMvcTest(controllers = PaymentResource.class)
class PaymentResourceTest {

	static final String API_PAYMENT = "/api/payments";
	
	@Autowired
	MockMvc mvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@MockBean
	PaymentService service;
	
	@Test
	@DisplayName("Should create a payment slip")
	void savePaymentSlip() throws Exception {

		PaymentDTO paymentDTO = UtilPayment.paymentSlipDTO();
		
		String json = objectMapper.writeValueAsString(paymentDTO);
		
		Mockito.when(service.save(Mockito.any(Payment.class))).thenReturn(UtilPayment.paymentSlip());
		
		RequestBuilder request = MockMvcRequestBuilders
				.post(API_PAYMENT)
				.accept(APPLICATION_JSON)
				.contentType(APPLICATION_JSON)
				.content(json);
		
		mvc.perform(request)
			.andExpect(status().isCreated())
			.andExpect(result -> assertTrue(result.getResponse().getContentAsString().contains("SLIP")))
			.andExpect(jsonPath("type").value(PaymentType.PAYMENT_SLIP.name()))
			.andExpect(jsonPath("id").value(paymentDTO.getUuid()));
		
		
	}
	
	@Test
	@DisplayName("Should create a payment card")
	void savePaymentCard() throws Exception {

		PaymentDTO paymentDTO = UtilPayment.paymentCardDTO();
		
		String json = objectMapper.writeValueAsString(paymentDTO);
		
		Mockito.when(service.save(Mockito.any(Payment.class))).thenReturn(UtilPayment.paymentCreditCard());
		
		RequestBuilder request = MockMvcRequestBuilders
				.post(API_PAYMENT)
				.accept(APPLICATION_JSON)
				.contentType(APPLICATION_JSON)
				.content(json);
		
		mvc.perform(request)
			.andExpect(status().isCreated())
			.andExpect(result -> assertTrue(result.getResponse().getContentAsString().contains("CARD")))
			.andExpect(jsonPath("type").value(PaymentType.PAYMENT_CREDIT_CARD.name()))
			.andExpect(jsonPath("id").value(paymentDTO.getUuid()));
		
		
	}

	@Test
	@DisplayName("Should throw exception create a invalid payment slip")
	void saveInvalidPaymentSlip() throws Exception {
		
		String json = objectMapper.writeValueAsString(new PaymentSlipDTO());
		
		RequestBuilder request = MockMvcRequestBuilders
				.post(API_PAYMENT)
				.accept(APPLICATION_JSON)
				.contentType(APPLICATION_JSON)
				.content(json);
		
		mvc.perform(request)
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("errors", hasSize(2)))
			.andExpect(jsonPath("message").value(HttpStatus.BAD_REQUEST.name()))
			.andExpect(jsonPath("code").value(HttpStatus.BAD_REQUEST.value()));
	}

	@Test
	@DisplayName("Should throw exception create a invalid payment card")
	void saveInvalidPaymentCard() throws Exception {
		
		String json = objectMapper.writeValueAsString(new PaymentCreditCardDTO());
		
		RequestBuilder request = MockMvcRequestBuilders
				.post(API_PAYMENT)
				.accept(APPLICATION_JSON)
				.contentType(APPLICATION_JSON)
				.content(json);
		
		mvc.perform(request)
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("errors", hasSize(4)))
			.andExpect(jsonPath("message").value(HttpStatus.BAD_REQUEST.name()))
			.andExpect(jsonPath("code").value(HttpStatus.BAD_REQUEST.value()));
		
	}

	@Test
	@DisplayName("Should found a payment by uuid")
	void findPaymentById() throws Exception {
		
		String id = "a0d7a0bf-ac09-4af6-b56f-13c1277a6b52";
		
		when(service.findByUuid(anyString())).thenReturn(UtilPayment.paymentSlip());
		
		RequestBuilder request = MockMvcRequestBuilders
				.get(API_PAYMENT.concat("/"+id))
				.accept(APPLICATION_JSON);
		
		mvc.perform(request)
			.andExpect(status().isOk())
			.andExpect(jsonPath("id").value(id));
	}
	
	@Test
	@DisplayName("Should throw exception found a payment by uuid")
	void findPaymentByInexistentId() throws Exception {
		
		String id = "a0d7a0bf-ac09-4af6-b56f-13c1277a6b52";
		String message = "Payment not found. id: " + id;
		
		when(service.findByUuid(anyString())).thenThrow(new EntityNotFoundException(message));
		
		RequestBuilder request = MockMvcRequestBuilders
				.get(API_PAYMENT.concat("/"+id))
				.accept(APPLICATION_JSON);
		
		mvc.perform(request)
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("errors", hasSize(1)))
			.andExpect(jsonPath("errors[0]").value(message));
		
	}
	
	@Test
	@DisplayName("Should found payments by Filter")
	void findByPaymentFilter() throws Exception {
		
		var payment = UtilPayment.paymentSlip();
		var filter = PaymentFilter
				.builder()
				.type(PaymentType.PAYMENT_SLIP.toString())
				.status(PaymentStatus.RECEIVED.toString())
				.build();
		
		when(service.findByFilter(any(Payment.class), any(Pageable.class)))
			.thenReturn(new PageImpl<>(Arrays.asList(payment), PageRequest.of(0, 100), 1));
	
		String queryString = String.format("?type=%s&status=%s&page=0&size=100", "payment_slip", 
				filter.getType(), filter.getStatus());
		
		RequestBuilder request = MockMvcRequestBuilders
				.get(API_PAYMENT.concat(queryString))
				.accept(APPLICATION_JSON);
		
		mvc.perform(request)
			.andExpect(status().isOk())
			.andExpect(jsonPath("content", Matchers.hasSize(1)))
			.andExpect(jsonPath("totalElements").value(1))
			.andExpect(jsonPath("pageable.pageSize").value(100))
			.andExpect(jsonPath("pageable.pageNumber").value(0));
		
	}
	
	@Test
	@DisplayName("Should reverse a payment")
	void chargebackPayment() throws Exception {
		
		PaymentSlip paymentSlip = UtilPayment.paymentSlip();
		paymentSlip.setId(1l);
		paymentSlip.setStatus(PaymentStatus.REVERSED);
		
		String uuid = paymentSlip.getUuid();
		
		when(service.chargeback(Mockito.anyString())).thenReturn(paymentSlip);
		
		RequestBuilder request = MockMvcRequestBuilders
				.put(API_PAYMENT.concat("/" + uuid))
				.accept(APPLICATION_JSON);
		
		mvc.perform(request)
			.andExpect(status().isOk())
			.andExpect(jsonPath("id").value(paymentSlip.getUuid()))
			.andExpect(jsonPath("status").value(PaymentStatus.REVERSED.toString()));
	}
	
	@Test
	@DisplayName("Should throw a invalid reverse a payment")
	void invalidChargebackPayment() throws Exception {
		
		String uuid = "a0d7a0bf-ac09-4af6-b56f-13c1277a6b52";
		
		String message = "The payment can't be reversed";
		when(service.chargeback(Mockito.anyString())).thenThrow(new BusinessException(message));
		
		RequestBuilder request = MockMvcRequestBuilders
				.put(API_PAYMENT.concat("/" + uuid))
				.accept(APPLICATION_JSON);
		
		mvc.perform(request)
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("errors", hasSize(1)))
			.andExpect(jsonPath("message").value(HttpStatus.BAD_REQUEST.name()))
			.andExpect(jsonPath("code").value(HttpStatus.BAD_REQUEST.value()));
	}
	
}
