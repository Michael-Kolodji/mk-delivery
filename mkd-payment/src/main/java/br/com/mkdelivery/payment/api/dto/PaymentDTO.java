package br.com.mkdelivery.payment.api.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.mkdelivery.payment.api.domain.enums.PaymentType;
import br.com.mkdelivery.payment.api.domain.enums.StatusPagamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

	@JsonProperty("id")
	private String uuid;
	private StatusPagamento statusPagamento; 
	private LocalDate dueDate;
	private String barCode;
	private String cardHolder;
	private String cardNumber;
	private String cvv;
	private LocalDate expirationDate;
	private PaymentType paymentType;
	
}
