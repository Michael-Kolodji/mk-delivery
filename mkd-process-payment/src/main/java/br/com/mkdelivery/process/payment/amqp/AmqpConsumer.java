package br.com.mkdelivery.process.payment.amqp;

public interface AmqpConsumer<T> {
	void consumer(T t);
}
