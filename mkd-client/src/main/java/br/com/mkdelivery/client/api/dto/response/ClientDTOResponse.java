package br.com.mkdelivery.client.api.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.mkdelivery.client.api.dto.PhoneDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTOResponse {

	private String uuid;
	private String name;
	private String cpf;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;
	private List<PhoneDTO> phones;
	private String username;
	
}
