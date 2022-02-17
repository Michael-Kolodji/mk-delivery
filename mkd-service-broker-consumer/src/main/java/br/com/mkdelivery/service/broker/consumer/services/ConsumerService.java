package br.com.mkdelivery.service.broker.consumer.services;

import br.com.mkdelivery.service.broker.consumer.api.dto.MessageQueue;

public interface ConsumerService {

	void action(MessageQueue message);

	void actionSendMail(MessageQueue message);

}
