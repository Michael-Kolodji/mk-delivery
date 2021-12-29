package br.com.mkdelivery.process.payment.services.impl;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.mkdelivery.process.payment.dto.MessageQueue;

@Component
@FeignClient(name = "service-broker", path = "amqp/producer", url = "localhost:8081")
public interface RabbitMQFeignClient {

	@PostMapping("/sendToConsumer")
	void sendMessage(@RequestBody MessageQueue message);
	
}
