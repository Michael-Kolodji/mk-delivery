package br.com.mkdelivery.payment.api.resources;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.mkdelivery.payment.api.domain.models.Payment;
import br.com.mkdelivery.payment.api.dto.PaymentCardDTO;
import br.com.mkdelivery.payment.api.dto.PaymentDTO;
import br.com.mkdelivery.payment.api.dto.PaymentSlipDTO;
import br.com.mkdelivery.payment.service.PaymentService;
import br.com.mkdelivery.payment.util.UtilPayment;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WebMvcTest(controllers = PaymentResource.class)
class PaymentResourceTest {

	private static final String API_PAYMENT = "/api/payments";
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private PaymentService service;
	
	@Test
	@DisplayName("Should create a payment slip")
	void savePaymentSlip() throws Exception {

		PaymentDTO paymentDTO = UtilPayment.paymentSlipDTO();
		
		String json = objectMapper.writeValueAsString(paymentDTO);
		
		Mockito.when(service.save(Mockito.any(Payment.class))).thenReturn(UtilPayment.paymentSlip());
		
		RequestBuilder request = MockMvcRequestBuilders
				.post(API_PAYMENT)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json);
		
		mvc.perform(request)
			.andExpect(status().isCreated())
			.andExpect(result -> assertTrue(result.getResponse().getContentAsString().contains("SLIP")))
			.andExpect(jsonPath("type").value("SLIP"))
			.andExpect(jsonPath("id").value(paymentDTO.getUuid()));
		
		
	}
	
	@Test
	@DisplayName("Should create a payment card")
	void savePaymentCard() throws Exception {

		PaymentDTO paymentDTO = UtilPayment.paymentCardDTO();
		
		String json = objectMapper.writeValueAsString(paymentDTO);
		
		Mockito.when(service.save(Mockito.any(Payment.class))).thenReturn(UtilPayment.paymentCard());
		
		RequestBuilder request = MockMvcRequestBuilders
				.post(API_PAYMENT)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json);
		
		mvc.perform(request)
			.andExpect(status().isCreated())
			.andExpect(result -> assertTrue(result.getResponse().getContentAsString().contains("CARD")))
			.andExpect(jsonPath("type").value("CARD"))
			.andExpect(jsonPath("id").value(paymentDTO.getUuid()));
		
		
	}
	

	@Test
	@DisplayName("Should throw exception create a payment slip")
	void saveInvalidPaymentSlip() throws Exception {
		
		String json = objectMapper.writeValueAsString(new PaymentSlipDTO());
		
		RequestBuilder request = MockMvcRequestBuilders
				.post(API_PAYMENT)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json);
		
		mvc.perform(request)
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("errors", hasSize(2)))
			.andExpect(jsonPath("message").value(HttpStatus.BAD_REQUEST.name()))
			.andExpect(jsonPath("code").value(HttpStatus.BAD_REQUEST.value()));
	}
	

	@Test
	@DisplayName("Should throw exception create a payment card")
	void saveInvalidPaymentCard() throws Exception {
		
		String json = objectMapper.writeValueAsString(new PaymentCardDTO());
		
		RequestBuilder request = MockMvcRequestBuilders
				.post(API_PAYMENT)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json);
		
		mvc.perform(request)
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("errors", hasSize(4)))
			.andExpect(jsonPath("message").value(HttpStatus.BAD_REQUEST.name()))
			.andExpect(jsonPath("code").value(HttpStatus.BAD_REQUEST.value()));
		
	}
}
