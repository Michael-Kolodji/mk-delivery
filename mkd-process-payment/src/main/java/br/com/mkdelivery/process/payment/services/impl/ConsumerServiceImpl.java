package br.com.mkdelivery.process.payment.services.impl;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.mkdelivery.process.payment.dto.MessageQueue;
import br.com.mkdelivery.process.payment.dto.PaymentDTO;
import br.com.mkdelivery.process.payment.enums.PaymentStatus;
import br.com.mkdelivery.process.payment.services.ConsumerService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsumerServiceImpl implements ConsumerService {

	private final ObjectMapper mapper;
	private final RabbitMQFeignClient feignClient;
	
	@Override
    public void action(MessageQueue message) {

		try {
			PaymentDTO payment = mapper.readValue(message.getText(), PaymentDTO.class);
			if(Math.round(Math.random() * 10) % 2 == 0) {
				payment.setStatus(PaymentStatus.APPROVED);
			} else {
				payment.setStatus(PaymentStatus.CANCELED);
			}
			
			message.setText(mapper.writeValueAsString(payment));
			feignClient.sendMessage(message);
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        
    }
	
}
