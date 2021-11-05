package br.com.mkdelivery.payment.api.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;

import br.com.mkdelivery.payment.api.domain.models.Payment;
import br.com.mkdelivery.payment.api.domain.models.PaymentSlip;
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
public class PaymentSlipDTO extends PaymentDTO {
	
	@NotNull
	private LocalDate dueDate;
	@NotNull
	private String barCode;
	
	@Override
	public Payment convertToPayment(PaymentDTO dto) {
		PaymentSlip payment = null;
		if(dto instanceof PaymentSlipDTO) {
			payment = new ModelMapper().map(dto, PaymentSlip.class);
		} else if (nextProcessor != null) {
            return nextProcessor.convertToPayment(dto);
        }
		return payment;
	}

	@Override
	public PaymentDTO convertToDTO(Payment payment) {
		PaymentSlipDTO dto = null;
		if(payment instanceof PaymentSlip) {
			dto = new ModelMapper().map(payment, PaymentSlipDTO.class);
		} else if (nextProcessor != null) {
            return nextProcessor.convertToDTO(payment);
        }
		return dto;
	}
	
}
