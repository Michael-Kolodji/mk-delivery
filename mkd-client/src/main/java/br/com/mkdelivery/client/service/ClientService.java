package br.com.mkdelivery.client.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.mkdelivery.client.api.domain.models.Client;

public interface ClientService {

	Client create(Client client);

	Client findByUuid(String uuid);

	Page<Client> findClients(Client filter, Pageable pageable);

	Client update(String uuid, Client client);

	void delete(String uuid);
	
}
