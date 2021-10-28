package br.com.mkdelivery.payment.api.domain.models;

import java.util.UUID;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.mkdelivery.payment.api.domain.enums.StatusPagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "PAYMENT")
@DiscriminatorColumn(name = "payment_type", discriminatorType = DiscriminatorType.STRING)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	protected Long id;
	
	@Getter
	protected String uuid;
	
	@NotNull
	protected StatusPagamento statusPagamento; 
	
	public void setUuid(String uuid) {
		if(uuid != null) {
			this.uuid = uuid;
		}
	}
	
	public void generateUuid() {
		this.uuid = UUID.randomUUID().toString();
	}
}
