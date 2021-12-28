package br.com.mkdelivery.service.broker.consumer;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableRabbit
@EnableScheduling
public class MkdServiceBrokerConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MkdServiceBrokerConsumerApplication.class, args);
	}

}
