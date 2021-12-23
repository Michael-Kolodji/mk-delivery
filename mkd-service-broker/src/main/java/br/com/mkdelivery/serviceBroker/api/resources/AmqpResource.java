package br.com.mkdelivery.serviceBroker.api.resources;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.mkdelivery.serviceBroker.api.dto.MessageQueue;
import br.com.mkdelivery.serviceBroker.service.AmqpService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/amqp")
@RequiredArgsConstructor
public class AmqpResource {

	private final AmqpService service;
	
	@PostMapping("/send")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void sendToConsumer(@RequestBody MessageQueue message) {
		service.sendToConsumer(message);
	}
	
}
