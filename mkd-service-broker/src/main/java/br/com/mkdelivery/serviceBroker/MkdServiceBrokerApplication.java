package br.com.mkdelivery.serviceBroker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class MkdServiceBrokerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MkdServiceBrokerApplication.class, args);
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	
}
