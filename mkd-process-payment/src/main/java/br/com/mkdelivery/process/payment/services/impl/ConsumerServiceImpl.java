package br.com.mkdelivery.process.payment.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.mkdelivery.process.payment.dto.MessageQueue;
import br.com.mkdelivery.process.payment.dto.PaymentDTO;
import br.com.mkdelivery.process.payment.dto.PaymentResponseDTO;
import br.com.mkdelivery.process.payment.enums.PaymentStatus;
import br.com.mkdelivery.process.payment.services.ConsumerService;
import br.com.mkdelivery.process.payment.services.RabbitMQFeignClient;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsumerServiceImpl implements ConsumerService {

	private final ObjectMapper mapper;
	private final ModelMapper modelMapper;
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
			
			PaymentResponseDTO responseDTO = modelMapper.map(payment, PaymentResponseDTO.class);
			
			message.setText(mapper.writeValueAsString(responseDTO));
			
			feignClient.sendMessage(message);
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        
    }
	
}
