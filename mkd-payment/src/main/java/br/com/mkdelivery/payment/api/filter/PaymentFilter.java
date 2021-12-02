package br.com.mkdelivery.payment.api.filter;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentFilter {

	private String type;
	private String status;
	
}
