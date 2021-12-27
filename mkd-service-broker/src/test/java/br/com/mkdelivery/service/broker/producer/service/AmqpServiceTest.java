package br.com.mkdelivery.service.broker.producer.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.mkdelivery.service.broker.producer.amqp.impl.ProducerRabbitMQ;
import br.com.mkdelivery.service.broker.producer.api.dto.MessageQueue;
import br.com.mkdelivery.service.broker.producer.service.AmqpService;
import br.com.mkdelivery.service.broker.producer.service.impl.RabbitMQServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class AmqpServiceTest {

	AmqpService service;
	
	@MockBean
	RabbitTemplate rabbitTemplate;
	
	@BeforeEach
	void setup() {
		service = new RabbitMQServiceImpl(new ProducerRabbitMQ(rabbitTemplate));
	}
	
	@Test
	@DisplayName("Should register a item in the queue")
	void sendToConsumer() {
		
		doNothing().when(rabbitTemplate).convertAndSend(anyString(), anyString(), any(MessageQueue.class));
		
		MessageQueue message = MessageQueue.builder().text("teste").build();
		
		service.sendToConsumer(message);
		
		verify(rabbitTemplate).convertAndSend(null, null, message);
	}
	
}
