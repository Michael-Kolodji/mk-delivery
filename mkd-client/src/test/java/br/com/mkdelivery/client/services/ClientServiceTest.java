package br.com.mkdelivery.client.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.mkdelivery.client.api.domain.models.Client;
import br.com.mkdelivery.client.api.domain.repositories.ClientRepository;
import br.com.mkdelivery.client.service.ClientService;
import br.com.mkdelivery.client.services.exception.BusinessException;
import br.com.mkdelivery.client.services.impl.ClientServiceImpl;
import br.com.mkdelivery.client.util.Util;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class ClientServiceTest {

	private ClientService service;
	
	@MockBean
	private ClientRepository repository;
	
//	@MockBean
//	private BCryptPasswordEncoder passwordEncoder;
	
	@BeforeEach
	void setUp() {
		this.service = new ClientServiceImpl(repository/*, passwordEncoder*/);
	}
	
	@Test
	@DisplayName("Should create a client")
	void createClient() {
		Client client = Util.createClient();
		client.setId(1l);
		
		when(repository.save(Mockito.any(Client.class))).thenReturn(client);
		
		Client clientCreated = service.create(Util.createClient());
		
		assertThat(clientCreated).isNotNull();
		assertThat(clientCreated.getId()).isEqualTo(1l);
		
	}
	
	@Test
	@DisplayName("Should throw exception when create a client wirth duplicated cpf")
	void createClientWithDuplicatedCPF() {
		
		when(repository.existsByCpf(anyString())).thenReturn(true);
		
		Throwable throwable = Assertions.catchThrowable(() -> service.create(Util.createClient()));
		
		assertThat(throwable)
			.isInstanceOf(BusinessException.class)
			.hasMessage("CPF already registered!");
	}
	
	@Test
	@DisplayName("Should return a client when find by id")
	void findClientById() {
		
		var client = Util.createClient();
		
		when(repository.findByUuid(anyString())).thenReturn(Optional.of(client));
		
		Client clientFounded = service.findByUuid(client.getUuid());
		
		assertThat(clientFounded).isNotNull();
		
	}
	
	@Test
	@DisplayName("Should list clients with pagination")
	@SuppressWarnings("unchecked")
	void listClientsWithPagination() throws Exception {
		
		var client = Util.createClient();
		
		PageRequest pageRequest = PageRequest.of(0, 10);
		List<Client> clients = Arrays.asList(client);
		PageImpl<Client> page = new PageImpl<>(clients, pageRequest, 1);
		
		when(repository.findAll(Mockito.any(Example.class), Mockito.any(PageRequest.class)))
		.thenReturn(page);
		
		Page<Client> result = service.findClients(client, pageRequest);
		
		assertThat(result.getTotalElements()).isEqualTo(1);
		assertThat(result.getContent()).isEqualTo(clients);
		assertThat(result.getPageable().getPageNumber()).isZero();
		assertThat(result.getPageable().getPageSize()).isEqualTo(10);
		
	}
	
	@Test
	@DisplayName("Should update a client")
	void updateClient() {
		
		Client client = Util.createClient();
		
		when(repository.findByUuid(anyString())).thenReturn(Optional.of(client));
		when(repository.save(Mockito.any(Client.class))).thenReturn(client);
		
		Client clientUpdate = service.update(client.getUuid(), client);
		
		assertThat(clientUpdate).isNotNull();
		assertThat(clientUpdate.getUuid()).isEqualTo(client.getUuid());
		assertThat(clientUpdate.getName()).isEqualTo(client.getName());
		assertThat(clientUpdate.getCpf()).isEqualTo(client.getCpf());
		assertThat(clientUpdate.getUsername()).isEqualTo(client.getUsername());
		assertThat(clientUpdate.getPhones()).isNotEmpty();
		assertThat(clientUpdate.getPhones().get(0).getDdd()).isEqualTo(client.getPhones().get(0).getDdd());
		assertThat(clientUpdate.getPhones().get(0).getPhoneNumber()).isEqualTo(client.getPhones().get(0).getPhoneNumber());
		
	}
	
	@Test
	@DisplayName("Should throw exception when update a inexistent client")
	void updateInexistentClient() {
				
		Client client = Util.createClient();		
		String uuid = client.getUuid();
		
		when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		
		Throwable throwable = catchThrowable(() -> service.update(uuid, client));
		
		assertThat(throwable)
			.isInstanceOf(EntityNotFoundException.class)
			.hasMessage(String.format("Client with id: %s not found!", uuid));
		
	}
	
	@Test
	@DisplayName("Should throw exception when update a inexistent client")
	void updateClientWithInconsistentID() {
		
		String uuid = "jkljvkljxkljxvjkcx";
		
		Client client = Util.createClient();
		
		Throwable throwable = catchThrowable(() -> service.update(uuid, client));
		
		assertThat(throwable)
			.isInstanceOf(BusinessException.class)
			.hasMessage(String.format("Client with inconsistent: %s!", uuid));
		
	}
	
	@Test
	@DisplayName("Should delete a client")
	void deleteClient() {
				
		Client client = Util.createClient();
		
		when(repository.findByUuid(anyString())).thenReturn(Optional.of(client));
		
		org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> service.delete(client.getUuid()));
		
		Mockito.verify(repository).delete(client);
		
	}
	
	@Test
	@DisplayName("Should delete a not found client")
	void deleteClientNotFound() throws Exception {
		String uuid = Util.createClient().getUuid();
		
		Throwable throwable = catchThrowable(() -> service.delete(uuid));
		
		assertThat(throwable)
		.isInstanceOf(EntityNotFoundException.class)
		.hasMessage(String.format("Client with id: %s not found!", uuid));
		
	}
	
	
}
