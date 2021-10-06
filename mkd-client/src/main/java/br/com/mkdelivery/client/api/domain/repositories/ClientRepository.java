package br.com.mkdelivery.client.api.domain.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mkdelivery.client.api.domain.models.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

	boolean existsByCpf(String cpf);

	Optional<Client> findByUuid(String uuid);

}
