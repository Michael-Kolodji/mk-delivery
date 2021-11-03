package br.com.mkdelivery.payment.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import br.com.mkdelivery.payment.api.domain.enums.StatusPagamento;
import br.com.mkdelivery.payment.api.domain.models.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public abstract class PaymentDTO {

	@JsonProperty("id")
	protected String uuid;
	
	protected StatusPagamento statusPagamento;

	protected PaymentDTO nextProcessor;

	public abstract Payment convertToPayment(PaymentDTO dto);
	
	public abstract PaymentDTO convertToDTO(Payment payment);

}
