package br.com.mkdelivery.client.services.impl;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.mkdelivery.client.api.domain.models.Client;
import br.com.mkdelivery.client.api.domain.repositories.ClientRepository;
import br.com.mkdelivery.client.service.ClientService;
import br.com.mkdelivery.client.services.exception.BusinessException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

	private final ClientRepository repository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Override
	public Client create(Client client) {
		
		if(repository.existsByCpf(client.getCpf())) {
			throw new BusinessException("CPF already registered!");
		}
		
		if(client.getPassword() == null) {
			throw new BusinessException("Password not be null");
		}
		
		client.generateUuid();
		
		client.setPassword(passwordEncoder.encode(client.getPassword()));
		
		return repository.save(client);
		
	}

	@Override
	public Client findByUuid(String uuid) {
		return repository.findByUuid(uuid)
				.orElseThrow(() -> 
					new EntityNotFoundException(
							String.format("Client with id: %s not found!", uuid)));
	}

	@Override
	public Page<Client> findClients(Client filter, Pageable pageable) {
		
		Example<Client> example = Example.of(filter, 
				ExampleMatcher
					.matching()
					.withIgnoreCase()
					.withIgnoreNullValues()
					.withStringMatcher(StringMatcher.CONTAINING));
		
		return repository.findAll(example, pageable);
		
	}

	@Override
	public Client update(String uuid, Client client) {
		if(!uuid.equals(client.getUuid())) {
			throw new BusinessException(String.format("Client with inconsistent id: %s!", uuid));
		}
		
		Client clientFounded = findByUuid(uuid);
		
		if(clientFounded == null) {
			throw new EntityNotFoundException(String.format("Client with id: %s not found!", uuid));
		}
		
		BeanUtils.copyProperties(client, clientFounded, "id", "password");
		
		return repository.save(clientFounded);
	}

	@Override
	public void delete(String uuid) {
		repository.delete(findByUuid(uuid));		
	}

}
