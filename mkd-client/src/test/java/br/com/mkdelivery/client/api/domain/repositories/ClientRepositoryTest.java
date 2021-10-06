package br.com.mkdelivery.client.api.domain.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.mkdelivery.client.api.domain.models.Client;
import br.com.mkdelivery.client.util.Util;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
class ClientRepositoryTest {

	@Autowired
	private TestEntityManager manager;
	
	@Autowired
	private ClientRepository repository;
	
	@Test
	@DisplayName("Should create a client")
	void createClient() {
		
		Client savedClient = repository.save(Util.createClient());
		
		assertThat(savedClient.getId()).isNotNull();
		assertThat(savedClient.getId()).isNotNegative();
		
	}
	
	@Test
	@DisplayName("Should return a client when find by id")
	void findClientByUuid() {
		
		Client client = manager.persist(Util.createClient());
		
		Optional<Client> clientFound = repository.findByUuid(client.getUuid());
		
		assertThat(clientFound).isPresent();
		
	}
	
	@Test
	@DisplayName("Should return clients with pagination")
	void findClientsWithPagination() {
		
		var client = manager.persist(Util.createClient());
		
		PageRequest pageRequest = PageRequest.of(0, 10);
		
		Example<Client> example = Example.of(client, 
				ExampleMatcher
					.matching()
					.withIgnoreCase()
					.withIgnoreNullValues()
					.withStringMatcher(StringMatcher.CONTAINING));
		
		Page<Client> result = repository.findAll(example, pageRequest);
		
		assertThat(result.getContent()).isNotEmpty();
		assertThat(result.getTotalElements()).isEqualTo(1);
		assertThat(result.getPageable().getPageNumber()).isZero();
		assertThat(result.getPageable().getPageSize()).isEqualTo(10);
		
	}
	
	@Test
	@DisplayName("Should update a client")
	void updateClient() {
		
		Client client = manager.persist(Util.createClient());
		
		client.setName("Antonio");
		
		Client clientUpdated = repository.save(client);
		
		assertThat(clientUpdated.getName()).isEqualTo(client.getName());
		
	}
	
	@Test
	@DisplayName("Should delete a client")
	void deleteClient() {
		
		Client client = manager.persist(Util.createClient());
		
		repository.delete(client);
		
		Optional<Client> clientDeleted = repository.findById(client.getId());
		
		assertThat(clientDeleted).isNotPresent();
		
	}
	
}
