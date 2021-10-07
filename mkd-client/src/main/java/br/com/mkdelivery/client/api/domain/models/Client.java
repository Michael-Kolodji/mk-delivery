package br.com.mkdelivery.client.api.domain.models;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_client")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@Getter
	private String uuid;
	
	@NotBlank(message = "Name not be null")
	private String name;
	
	@NotBlank(message = "CPF not be null")
	private String cpf;
	
	@NotNull(message = "BirthDate not be null")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;
	
	@NotNull(message = "Phone not be null")
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	private List<Phone> phones;
	
	@NotBlank(message = "Username not be null")
	private String username;
	
	@NotBlank(message = "Password not be null")
	private String password;
	
	public void setUuid(String uuid) {
		if(uuid != null) {
			this.uuid = uuid;
		}
	}
	
	public void generateUuid() {
		this.uuid = UUID.randomUUID().toString();
	}
	
}
