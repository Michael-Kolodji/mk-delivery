package br.com.mkdelivery.payment.service.impl;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.mkdelivery.payment.api.domain.models.Payment;
import br.com.mkdelivery.payment.api.dto.MessageQueue;
import br.com.mkdelivery.payment.feignClient.ProcessPaymentFeignClient;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProcessPayment {

	private final ProcessPaymentFeignClient feignClient;
	private final ObjectMapper mapper;
	
	public void sendPayment(Payment payment) {
		
		try {
			MessageQueue messageQueue = MessageQueue.builder().text(mapper.writeValueAsString(payment)).build();
			feignClient.sendMessage(messageQueue);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}

}
