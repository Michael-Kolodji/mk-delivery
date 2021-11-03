package br.com.mkdelivery.payment.api.dto;

import java.time.LocalDate;

import org.modelmapper.ModelMapper;

import br.com.mkdelivery.payment.api.domain.models.Payment;
import br.com.mkdelivery.payment.api.domain.models.PaymentCard;
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
public class PaymentCardDTO extends PaymentDTO {

	private String cardHolder;
	private String cardNumber;
	private String cvv;
	private LocalDate expirationDate;
	
	@Override
	public Payment convertToPayment(PaymentDTO dto) {
		PaymentCard payment = null;
		if(dto instanceof PaymentCardDTO) {
			payment = new ModelMapper().map(dto, PaymentCard.class);
		} else if (nextProcessor != null) {
            return nextProcessor.convertToPayment(dto);
        }
		return payment;
	}
	
	@Override
	public PaymentDTO convertToDTO(Payment payment) {
		PaymentCardDTO dto = null;
		if(payment instanceof PaymentCard) {
			dto = new ModelMapper().map(payment, PaymentCardDTO.class);
		} else if (nextProcessor != null) {
            return nextProcessor.convertToDTO(payment);
        }
		return dto;
	}
	
}