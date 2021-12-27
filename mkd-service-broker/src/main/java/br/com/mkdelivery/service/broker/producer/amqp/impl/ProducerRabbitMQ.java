package br.com.mkdelivery.service.broker.producer.amqp.impl;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.mkdelivery.service.broker.producer.amqp.AmqpProducer;
import br.com.mkdelivery.service.broker.producer.api.dto.MessageQueue;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProducerRabbitMQ implements AmqpProducer<MessageQueue> {

	private final RabbitTemplate rabbitTemplate;
	
	@Value("${spring.rabbitmq.request.routing-key.producer}")
    private String queue;

    @Value("${spring.rabbitmq.request.exchenge.producer}")
    private String exchange;

	@Override
	public void producer(MessageQueue message) {
		try {
            rabbitTemplate.convertAndSend(exchange, queue, message);
        } catch (Exception ex) {
            throw new AmqpRejectAndDontRequeueException(ex);
        }
	}

}
