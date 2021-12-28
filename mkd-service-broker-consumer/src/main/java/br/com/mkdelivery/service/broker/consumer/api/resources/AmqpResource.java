package br.com.mkdelivery.service.broker.consumer.api.resources;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.mkdelivery.service.broker.consumer.services.RePublishService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/amqp/consumer")
@RequiredArgsConstructor
public class AmqpResource {

	private final RePublishService service;
	
	@GetMapping("/repost")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void sendToQueue() {
		service.repost();
	}
	
}
