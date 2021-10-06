package br.com.mkdelivery.client.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTOCreatedResponse {

	private String uuid;
	private String name;
	private String username;
	
}
