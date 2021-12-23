package br.com.mkdelivery.serviceBroker.service.impl;

import org.springframework.stereotype.Service;

import br.com.mkdelivery.serviceBroker.amqp.AmqpProducer;
import br.com.mkdelivery.serviceBroker.api.dto.MessageQueue;
import br.com.mkdelivery.serviceBroker.service.AmqpService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RabbitMQServiceImpl implements AmqpService {

	private final AmqpProducer<MessageQueue> amqp;
	
	@Override
	public void sendToConsumer(MessageQueue message) {
		amqp.producer(message);
	}

}
