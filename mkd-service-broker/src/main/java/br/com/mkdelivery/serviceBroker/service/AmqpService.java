package br.com.mkdelivery.serviceBroker.service;

import br.com.mkdelivery.serviceBroker.api.dto.MessageQueue;

public interface AmqpService {

	void sendToConsumer(MessageQueue message);

}
