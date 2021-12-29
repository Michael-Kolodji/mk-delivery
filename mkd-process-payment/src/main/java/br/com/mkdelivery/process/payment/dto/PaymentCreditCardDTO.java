package br.com.mkdelivery.process.payment.dto;

import java.time.LocalDate;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("CARD")
public class PaymentCreditCardDTO extends PaymentDTO {

	private String cardHolder;
	private String cardNumber;
	private String cvv;
	private LocalDate expirationDate;
	
	@Override
	public PaymentDTO convertToDTO(PaymentDTO paymentDTO) {
		PaymentCreditCardDTO dto = null;
		if(paymentDTO instanceof PaymentCreditCardDTO) {
			dto = new ModelMapper().map(paymentDTO, PaymentCreditCardDTO.class);
		} else if (nextProcessor != null) {
            return nextProcessor.convertToDTO(paymentDTO);
        }
		return dto;
	}
	
}
