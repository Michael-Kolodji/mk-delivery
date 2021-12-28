package br.com.mkdelivery.service.broker.consumer.services.impl;

import org.springframework.stereotype.Service;

import br.com.mkdelivery.service.broker.consumer.amqp.AmqpRePublish;
import br.com.mkdelivery.service.broker.consumer.services.RePublishService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RePublishServiceImpl implements RePublishService {

	private final AmqpRePublish rePublish;
	
	@Override
	public void repost() {
		rePublish.rePublish();
	}

}
