package br.com.mkdelivery.payment.api.domain.models;

import java.time.LocalDate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "PAYMENT_CARD")
@DiscriminatorValue("PAYMENT_CARD")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PaymentCard extends Payment {

	@NotBlank
	private String cardHolder;
	
	@NotBlank
	private String cardNumber;
	
	@NotBlank
	private String cvv;
	
	@NotNull
	private LocalDate expirationDate;
	
}
