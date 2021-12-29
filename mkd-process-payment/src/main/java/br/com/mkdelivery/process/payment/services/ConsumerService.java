package br.com.mkdelivery.process.payment.services;

import br.com.mkdelivery.process.payment.dto.MessageQueue;

public interface ConsumerService {

	void action(MessageQueue message);

}
