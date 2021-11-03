package br.com.mkdelivery.payment.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.mkdelivery.payment.api.domain.models.Payment;
import br.com.mkdelivery.payment.api.dto.PaymentDTO;
import br.com.mkdelivery.payment.api.resources.PaymentResource;
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
	void savePaymentSlipDTO() throws Exception {

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
			.andExpect(result -> assertTrue(result.getResponse().getContentAsString().contains("PaymentSlipDTO")))
			.andExpect(jsonPath("id").value(paymentDTO.getUuid()));
		
		
	}
	
	@Test
	@DisplayName("Should create a payment card")
	void savePaymentCardDTO() throws Exception {

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
			.andExpect(result -> assertTrue(result.getResponse().getContentAsString().contains("PaymentCardDTO")))
			.andExpect(jsonPath("id").value(paymentDTO.getUuid()));
		
		
	}
	
}
