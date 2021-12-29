package br.com.mkdelivery.service.broker.producer.api.resources;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.mkdelivery.service.broker.producer.api.dto.MessageQueue;
import br.com.mkdelivery.service.broker.producer.service.AmqpService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/amqp/producer")
@RequiredArgsConstructor
public class AmqpResource {

	private final AmqpService service;
	
	@PostMapping("/sendToProducer")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void sendToProducer(@RequestBody MessageQueue message) {
		service.sendToProducer(message);
	}
	
	@PostMapping("/sendToConsumer")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void sendToConsumer(@RequestBody MessageQueue message) {
		service.sendToConsumer(message);
	}
	
}
