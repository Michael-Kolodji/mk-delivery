package br.com.mkdelivery.service.broker.producer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerRabbitConfiguration {

	@Value("${spring.rabbitmq.request.routing-key.consumer}")
	private String queue;

	@Value("${spring.rabbitmq.request.exchange.consumer}")
	private String exchange;

	@Value("${spring.rabbitmq.request.dead-letter.consumer}")
	private String deadLetter;

	@Value("${spring.rabbitmq.request.parking-lot.consumer}")
	private String parkingLot;

	@Bean
	DirectExchange exchangeConsumer() {
		return new DirectExchange(exchange);
	}

	@Bean
	Queue deadLetterConsumer() {
		return QueueBuilder.durable(deadLetter).deadLetterExchange(exchange).deadLetterRoutingKey(queue).build();
	}

	@Bean
	Queue queueConsumer() {
		return QueueBuilder.durable(queue).deadLetterExchange(exchange).deadLetterRoutingKey(deadLetter).build();
	}

	@Bean
	Queue parkingLotConsumer() {
		return new Queue(parkingLot);
	}

	@Bean
	public Binding bindingQueueConsumer() {
		return BindingBuilder.bind(queueConsumer()).to(exchangeConsumer()).with(queue);
	}

	@Bean
	public Binding bindingDeadLetterConsumer() {
		return BindingBuilder.bind(deadLetterConsumer()).to(exchangeConsumer()).with(deadLetter);
	}

	@Bean
	public Binding bindingParkingLotConsumer() {
		return BindingBuilder.bind(parkingLotConsumer()).to(exchangeConsumer()).with(parkingLot);
	}
}
