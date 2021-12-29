package br.com.mkdelivery.service.broker.producer.service.impl;

import org.springframework.stereotype.Service;

import br.com.mkdelivery.service.broker.producer.amqp.AmqpProducer;
import br.com.mkdelivery.service.broker.producer.api.dto.MessageQueue;
import br.com.mkdelivery.service.broker.producer.service.AmqpService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RabbitMQServiceImpl implements AmqpService {

	private final AmqpProducer<MessageQueue> amqp;
	
	@Override
	public void sendToProducer(MessageQueue message) {
		amqp.producer(message);
	}

	@Override
	public void sendToConsumer(MessageQueue message) {
		amqp.consumer(message);
	}

}
