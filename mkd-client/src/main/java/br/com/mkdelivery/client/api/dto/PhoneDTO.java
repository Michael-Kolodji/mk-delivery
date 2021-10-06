package br.com.mkdelivery.client.api.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDTO {

	private Long id;
	
	@NotEmpty
	private String ddd;
	
	@NotEmpty
	private String phoneNumber;
	
}
