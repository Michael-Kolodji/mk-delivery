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
@JsonTypeName("SLIP")
public class PaymentSlipDTO extends PaymentDTO {
	
	private LocalDate dueDate;
	private String barCode;
	
	@Override
	public PaymentDTO convertToDTO(PaymentDTO paymentDTO) {
		PaymentSlipDTO dto = null;
		if(paymentDTO instanceof PaymentSlipDTO) {
			dto = new ModelMapper().map(paymentDTO, PaymentSlipDTO.class);
		} else if (nextProcessor != null) {
            return nextProcessor.convertToDTO(paymentDTO);
        }
		return dto;
	}
	
}
