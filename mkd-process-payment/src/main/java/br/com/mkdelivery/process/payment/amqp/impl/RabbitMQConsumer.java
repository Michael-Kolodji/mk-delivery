package br.com.mkdelivery.process.payment.amqp.impl;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.com.mkdelivery.process.payment.amqp.AmqpConsumer;
import br.com.mkdelivery.process.payment.dto.MessageQueue;
import br.com.mkdelivery.process.payment.services.ConsumerService;
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
