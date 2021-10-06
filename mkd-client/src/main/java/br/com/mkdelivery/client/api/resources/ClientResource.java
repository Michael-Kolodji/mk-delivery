package br.com.mkdelivery.client.api.resources;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.mkdelivery.client.api.domain.models.Client;
import br.com.mkdelivery.client.api.dto.request.ClientDTORequest;
import br.com.mkdelivery.client.api.dto.response.ClientDTOCreatedResponse;
import br.com.mkdelivery.client.api.dto.response.ClientDTOResponse;
import br.com.mkdelivery.client.service.ClientService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientResource {

	private final ModelMapper mapper;
	private final ClientService service;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ClientDTOCreatedResponse create(@RequestBody @Valid ClientDTORequest dto) {
		var client = service.create(mapper.map(dto, Client.class));
		return mapper.map(client, ClientDTOCreatedResponse.class);
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public ClientDTOResponse findById(@PathVariable("id") String uuid) {
		return mapper.map(service.findByUuid(uuid), ClientDTOResponse.class);
	}
	
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public Page<ClientDTOResponse> findClients(ClientDTORequest dtoRequest, Pageable pageable) {
		Client filter = mapper.map(dtoRequest, Client.class); 
		
		Page<Client> result = service.findClients(filter, pageable);
		
		var clients = result.getContent()
				.stream()
				.map(client -> mapper.map(client, ClientDTOResponse.class))
				.collect(Collectors.toList());
		
		return new PageImpl<>(clients , pageable, result.getTotalElements());
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public ClientDTOResponse update(@PathVariable("id") String uuid, @RequestBody @Valid ClientDTORequest dtoRequest) {
		
		Client client = mapper.map(dtoRequest, Client.class);
		
		return mapper.map(service.update(uuid, client), ClientDTOResponse.class);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String uuid) {
		service.delete(uuid);
	}
	
}
