package br.com.mkdelivery.service.broker.consumer.services.impl;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.stereotype.Service;

import br.com.mkdelivery.service.broker.consumer.api.dto.MessageQueue;
import br.com.mkdelivery.service.broker.consumer.services.ConsumerService;

@Service
public class ConsumerServiceImpl implements ConsumerService {

	@Override
    public void action(MessageQueue message) {
//        if("teste".equalsIgnoreCase(message.getText())) {
//            throw new AmqpRejectAndDontRequeueException("erro");
//        }

        System.out.println(message.getText());
    }
	
}
