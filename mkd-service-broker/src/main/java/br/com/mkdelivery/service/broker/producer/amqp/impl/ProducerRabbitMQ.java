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
    private String queueProducer;

    @Value("${spring.rabbitmq.request.exchange.producer}")
    private String exchangeProducer;

    @Value("${spring.rabbitmq.request.routing-key.consumer}")
    private String queueConsumer;
    
    @Value("${spring.rabbitmq.request.exchange.consumer}")
    private String exchangeConsumer;

	@Override
	public void producer(MessageQueue message) {
		try {
            rabbitTemplate.convertAndSend(exchangeProducer, queueProducer, message);
        } catch (Exception ex) {
            throw new AmqpRejectAndDontRequeueException(ex);
        }
	}

	@Override
	public void consumer(MessageQueue message) {
		try {
            rabbitTemplate.convertAndSend(exchangeConsumer, queueConsumer, message);
        } catch (Exception ex) {
            throw new AmqpRejectAndDontRequeueException(ex);
        }
		
	}

}
