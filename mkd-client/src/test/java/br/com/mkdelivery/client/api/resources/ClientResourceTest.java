package br.com.mkdelivery.client.api.resources;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import javax.persistence.EntityNotFoundException;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.mkdelivery.client.api.domain.models.Client;
import br.com.mkdelivery.client.api.dto.request.ClientDTORequest;
import br.com.mkdelivery.client.service.ClientService;
import br.com.mkdelivery.client.util.Util;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WebMvcTest(controllers = ClientResource.class)
class ClientResourceTest {

	private static final String API_CLIENTS = "/api/clients";
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private ClientService service;
	
	@Test
	@DisplayName("Should create a client")
	void createClient() throws Exception {
		
		var clientDTO = Util.createClientDTOReq();
		
		String json = objectMapper.writeValueAsString(clientDTO);

		var client = Util.createClient();
		client.setId(1l);
		
		when(service.create(any(Client.class))).thenReturn(client);
		
		RequestBuilder request = MockMvcRequestBuilders
				.post(API_CLIENTS)
				.accept(APPLICATION_JSON)
				.contentType(APPLICATION_JSON)
				.content(json);
		
		mvc.perform(request)
			.andExpect(status().isCreated())
			.andExpect(jsonPath("uuid").value(client.getUuid()))
			.andExpect(jsonPath("name").value(clientDTO.getName()))
			.andExpect(jsonPath("username").value(clientDTO.getUsername()));
	}
	
	@Test
	@DisplayName("Should throw exception when create a invalid client")
	void createInvalidClient() throws Exception {
		
		String json = objectMapper.writeValueAsString(new ClientDTORequest());
		
		RequestBuilder request = MockMvcRequestBuilders
				.post(API_CLIENTS)
				.accept(APPLICATION_JSON)
				.contentType(APPLICATION_JSON)
				.content(json);
		
		mvc.perform(request)
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("errors", hasSize(5)))
		.andExpect(jsonPath("message").value(HttpStatus.BAD_REQUEST.name()))
		.andExpect(jsonPath("code").value(HttpStatus.BAD_REQUEST.value()));
	}
	
	@Test
	@DisplayName("Should throw exception when create a client with invalid cpf")
	void createClientWithInvalidCPF() throws Exception {
		
		ClientDTORequest dto = Util.createClientDTOReq();
		dto.setCpf("11111111");
		
		String json = objectMapper.writeValueAsString(dto);
		
		RequestBuilder request = MockMvcRequestBuilders
				.post(API_CLIENTS)
				.accept(APPLICATION_JSON)
				.contentType(APPLICATION_JSON)
				.content(json);
		
		mvc.perform(request)
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("errors", hasSize(1)))
		.andExpect(jsonPath("message").value(HttpStatus.BAD_REQUEST.name()))
		.andExpect(jsonPath("code").value(HttpStatus.BAD_REQUEST.value()));
		
		
	}
	
	@Test
	@DisplayName("Should return a client when find by id")
	void findClientById() throws Exception {
		
		var clientDTOReq = Util.createClientDTOReq();
		
		var client = Util.createClient();
		
		when(service.findByUuid(anyString())).thenReturn(client);
		
		RequestBuilder request = MockMvcRequestBuilders
			.get(API_CLIENTS.concat("/" + client.getUuid()))
			.accept(APPLICATION_JSON);
		
		mvc.perform(request)
			.andExpect(status().isOk())
			.andExpect(jsonPath("uuid").value(client.getUuid()))
			.andExpect(jsonPath("name").value(clientDTOReq.getName()))
			.andExpect(jsonPath("cpf").value(clientDTOReq.getCpf()))
			.andExpect(jsonPath("birthDate").value(clientDTOReq.getBirthDate().toString()))
			.andExpect(jsonPath("username").value(clientDTOReq.getUsername()))
			.andExpect(jsonPath("phones").isNotEmpty());
		
	}
	
	@Test
	@DisplayName("Should throw exception when find a client whith inexistent id")
	void findClientByInexistentId() throws Exception {
		
		String message = "Client with id: 1 not found!";
		
		when(service.findByUuid(anyString())).thenThrow(new EntityNotFoundException(message));
		
		RequestBuilder request = MockMvcRequestBuilders
				.get(API_CLIENTS.concat("/" + Util.createClient().getUuid()))
				.accept(APPLICATION_JSON);
			
			mvc.perform(request)
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("errors", hasSize(1)))
				.andExpect(jsonPath("errors[0]").value(message));
	}
	
	@Test
	@DisplayName("Should list clients with pagination")
	void listClientsWithPagination() throws Exception {
		
		var client = Util.createClient();
		
		when(service.findClients(any(Client.class), any(Pageable.class)))
			.thenReturn(new PageImpl<>(Arrays.asList(client ), PageRequest.of(0, 100), 1));
		
		String queryString = String.format("?name=%s&cpf=%s&birthDate=%s&username=%s&page=0&size=100", 
				client.getName(), client.getCpf(), client.getBirthDate(), client.getUsername());
		
		RequestBuilder request = MockMvcRequestBuilders
				.get(API_CLIENTS.concat(queryString))
				.accept(APPLICATION_JSON);
			
			mvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(jsonPath("content", Matchers.hasSize(1)))
				.andExpect(jsonPath("totalElements").value(1))
				.andExpect(jsonPath("pageable.pageSize").value(100))
				.andExpect(jsonPath("pageable.pageNumber").value(0));
	}
	
	@Test
	@DisplayName("Should update a client")
	void updateClient() throws Exception {
		
		var dtoRequest = Util.createClientDTOReq();
		
		String json = objectMapper.writeValueAsString(dtoRequest);
		
		Client client = Util.createClient();
		
		when(service.update(anyString(), Mockito.any(Client.class))).thenReturn(client);
		
		var request = MockMvcRequestBuilders
						.put(API_CLIENTS.concat("/" + client.getUuid()))
						.accept(APPLICATION_JSON)
						.contentType(APPLICATION_JSON)
						.content(json);
		
		mvc.perform(request)
			.andExpect(status().isOk())
			.andExpect(jsonPath("uuid").value(client.getUuid()))
			.andExpect(jsonPath("name").value(dtoRequest.getName()))
			.andExpect(jsonPath("cpf").value(dtoRequest.getCpf()))
			.andExpect(jsonPath("username").value(dtoRequest.getUsername()))
			.andExpect(jsonPath("phones").isNotEmpty())
			.andExpect(jsonPath("phones[0].ddd").value(dtoRequest.getPhones().get(0).getDdd()))
			.andExpect(jsonPath("phones[0].phoneNumber").value(dtoRequest.getPhones().get(0).getPhoneNumber()));
		
	}
	
	@Test
	@DisplayName("Should update a invalid client")
	void updateInvalidClient() throws Exception {
		
		String json = objectMapper.writeValueAsString(new ClientDTORequest());
		
		var request = MockMvcRequestBuilders
				.put(API_CLIENTS.concat("/" + Util.createClient().getUuid()))
				.accept(APPLICATION_JSON)
				.contentType(APPLICATION_JSON)
				.content(json);
		
		mvc.perform(request)
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("errors", hasSize(5)))
		.andExpect(jsonPath("message").value(HttpStatus.BAD_REQUEST.name()))
		.andExpect(jsonPath("code").value(HttpStatus.BAD_REQUEST.value()));
		
	}
	
	@Test
	@DisplayName("Should delete a client")
	void deleteClient() throws Exception {
		
		var request = MockMvcRequestBuilders.
				delete(API_CLIENTS.concat("/" + Util.createClient().getUuid()))
				.accept(APPLICATION_JSON);
		
		mvc.perform(request)
			.andExpect(status().isNoContent());
		
	}
	
}
