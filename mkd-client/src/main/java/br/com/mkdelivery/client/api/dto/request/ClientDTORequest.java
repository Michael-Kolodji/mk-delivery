package br.com.mkdelivery.client.api.dto.request;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.mkdelivery.client.api.dto.PhoneDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTORequest {

	@JsonProperty("id")
	private String uuid;
	
	@NotEmpty
	private String name;
	
	@CPF
	@NotEmpty
	private String cpf;
	
	@NotNull
	@Past
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate birthDate;
	
	@NotEmpty
	private List<PhoneDTO> phones;
	
	@NotEmpty
	private String username;
	
	private String password;
	
}
