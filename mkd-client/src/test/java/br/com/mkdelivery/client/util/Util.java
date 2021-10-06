package br.com.mkdelivery.client.util;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;

import br.com.mkdelivery.client.api.domain.models.Client;
import br.com.mkdelivery.client.api.domain.models.Phone;
import br.com.mkdelivery.client.api.dto.PhoneDTO;
import br.com.mkdelivery.client.api.dto.request.ClientDTORequest;

public class Util {

	public static ClientDTORequest createClientDTOReq() {
		return ClientDTORequest
				.builder()
				.name("Pedro Alvares Cabral")
				.cpf("401.438.910-30")
				.birthDate(LocalDate.of(1990, Month.JANUARY, 1))
				.phones(Arrays.asList(
							PhoneDTO.builder().ddd("11").phoneNumber("9 8888-8888").build(),
							PhoneDTO.builder().ddd("11").phoneNumber("9 8888-7777").build()
				))
				.username("pedro-alvares@mail.com")
				.password("123456")
				.build();
	}
	
	public static Client createClient() {
		Client client = new Client();
		
		List<Phone> phones = new ArrayList<>();
		
		BeanUtils.copyProperties(createClientDTOReq(), client);
		client.setUuid("a0d7a0bf-ac09-4af6-b56f-13c1277a6b52");
		
		createClientDTOReq().getPhones()
			.forEach(phoneDTO -> {
				phones.add(Phone
							.builder()
							.ddd(phoneDTO.getDdd())
							.phoneNumber(phoneDTO.getPhoneNumber())
							.build());
			});
		
		client.setPhones(phones);
		
		return client;
	}
	
}
