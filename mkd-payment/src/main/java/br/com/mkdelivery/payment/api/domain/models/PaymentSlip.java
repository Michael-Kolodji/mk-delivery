package br.com.mkdelivery.payment.api.domain.models;

import java.time.LocalDate;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "PAYMENT_SLIP")
@DiscriminatorValue("PAYMENT_SLIP")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PaymentSlip extends Payment {

	@NotNull
	private LocalDate dueDate;
	@NotNull
	private String barCode;
	
}
