package br.com.mkdelivery.payment.api.domain.models;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.mkdelivery.payment.api.domain.enums.PaymentStatus;
import br.com.mkdelivery.payment.api.domain.enums.PaymentType;
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
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	protected Long id;
	
	@Getter
	protected String uuid;
	
	@Transient
	@Column(name = "payment_type")
	protected PaymentType type;
	
	protected PaymentStatus status; 
	
	public void setUuid(String uuid) {
		if(uuid != null) {
			this.uuid = uuid;
		}
	}
	
	public void generateUuid() {
		this.uuid = UUID.randomUUID().toString();
	}
	
}
