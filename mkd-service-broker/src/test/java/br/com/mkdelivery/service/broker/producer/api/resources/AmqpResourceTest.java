package br.com.mkdelivery.service.broker.producer.api.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.mkdelivery.service.broker.producer.api.dto.MessageQueue;
import br.com.mkdelivery.service.broker.producer.api.resources.AmqpResource;
import br.com.mkdelivery.service.broker.producer.service.AmqpService;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WebMvcTest(controllers = AmqpResource.class)
class AmqpResourceTest {

	static final String API_AMQP = "/amqp";
	
	@Autowired
	MockMvc mvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@MockBean
	AmqpService service;
	
	@Test
	@DisplayName("Should register a item in the queue")
	void sendToConsumer() throws Exception {
		
		MessageQueue message = MessageQueue.builder().text("Teste").build();
		
		String json = objectMapper.writeValueAsString(message);
		
		doNothing().when(mock(AmqpService.class)).sendToConsumer(any(MessageQueue.class));
		
		RequestBuilder request = MockMvcRequestBuilders
				.post(API_AMQP.concat("/send"))
				.accept(APPLICATION_JSON)
				.contentType(APPLICATION_JSON)
				.content(json);
		
		mvc.perform(request)
			.andExpect(status().isAccepted());
		
	}
	
}
