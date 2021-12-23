package br.com.mkdelivery.serviceBroker.amqp;

public interface AmqpProducer<T> {
	void producer(T t);
}
