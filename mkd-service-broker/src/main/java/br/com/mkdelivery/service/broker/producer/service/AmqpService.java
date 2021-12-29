package br.com.mkdelivery.service.broker.producer.service;

import br.com.mkdelivery.service.broker.producer.api.dto.MessageQueue;

public interface AmqpService {

	void sendToConsumer(MessageQueue message);

	void sendToProducer(MessageQueue message);

}
