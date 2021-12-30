package br.com.mkdelivery.payment.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.mkdelivery.payment.api.dto.MessageQueue;

@Component
@FeignClient(name = "process-payment", url = "localhost:8081", path = "amqp/producer")
public interface ProcessPaymentFeignClient {

	@PostMapping("/sendToProducer")
	void sendMessage(@RequestBody MessageQueue message);
	
}
