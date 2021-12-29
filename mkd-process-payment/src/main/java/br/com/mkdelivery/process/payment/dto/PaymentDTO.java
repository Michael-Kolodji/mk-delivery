package br.com.mkdelivery.process.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

import br.com.mkdelivery.process.payment.enums.PaymentStatus;
import br.com.mkdelivery.process.payment.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "classType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = PaymentSlipDTO.class, name = "SLIP"),
    @JsonSubTypes.Type(value = PaymentCreditCardDTO.class, name = "CARD")
})
public abstract class PaymentDTO {

	protected String uuid;

	protected String email;
	
	protected PaymentType type;
	
	protected PaymentStatus status;

	@JsonIgnore
	protected PaymentDTO nextProcessor;

	public abstract PaymentDTO convertToDTO(PaymentDTO paymentDTO);
	
}
