package br.com.mkdelivery.service.broker.consumer.amqp.impl;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.com.mkdelivery.service.broker.consumer.amqp.AmqpConsumer;
import br.com.mkdelivery.service.broker.consumer.api.dto.MessageQueue;
import br.com.mkdelivery.service.broker.consumer.services.ConsumerService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RabbitMQConsumer implements AmqpConsumer<MessageQueue> {

	private final ConsumerService consumerService;
	
	@Override
	@RabbitListener(queues = "${spring.rabbitmq.request.routing-key.producer}")
	public void consumer(MessageQueue message) {
		try {
            consumerService.action(message);
        } catch (Exception ex) {
            throw new AmqpRejectAndDontRequeueException(ex);
        }		
	}

}
