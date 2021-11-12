package br.com.mkdelivery.payment.api.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.annotation.JsonTypeName;

import br.com.mkdelivery.payment.api.domain.models.Payment;
import br.com.mkdelivery.payment.api.domain.models.PaymentCreditCard;
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

	@NotEmpty
	private String cardHolder;
	@NotEmpty
	private String cardNumber;
	@NotEmpty
	private String cvv;
	@NotNull
	private LocalDate expirationDate;
	
	@Override
	public Payment convertToPayment(PaymentDTO dto) {
		PaymentCreditCard payment = null;
		if(dto instanceof PaymentCreditCardDTO) {
			payment = new ModelMapper().map(dto, PaymentCreditCard.class);
		} else if (nextProcessor != null) {
            return nextProcessor.convertToPayment(dto);
        }
		return payment;
	}
	
	@Override
	public PaymentDTO convertToDTO(Payment payment) {
		PaymentCreditCardDTO dto = null;
		if(payment instanceof PaymentCreditCard) {
			dto = new ModelMapper().map(payment, PaymentCreditCardDTO.class);
		} else if (nextProcessor != null) {
            return nextProcessor.convertToDTO(payment);
        }
		return dto;
	}
	
}
