package br.com.mkdelivery.service.broker.producer.amqp;

public interface AmqpProducer<T> {
	void producer(T t);
}
