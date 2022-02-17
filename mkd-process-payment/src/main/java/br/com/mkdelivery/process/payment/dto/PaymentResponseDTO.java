package br.com.mkdelivery.process.payment.dto;

import br.com.mkdelivery.process.payment.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {

	protected String uuid;

	protected String name;
	
	protected String email;
	
	protected PaymentStatus status;
	
}
