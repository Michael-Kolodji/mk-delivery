package br.com.mkdelivery.payment.api.resources;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.mkdelivery.payment.api.domain.enums.PaymentType;
import br.com.mkdelivery.payment.api.domain.models.Payment;
import br.com.mkdelivery.payment.api.domain.models.PaymentCard;
import br.com.mkdelivery.payment.api.domain.models.PaymentSlip;
import br.com.mkdelivery.payment.api.dto.PaymentDTO;
import br.com.mkdelivery.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentResource {

	private final ModelMapper mapper;
	private final PaymentService service;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public PaymentDTO save(@RequestBody PaymentDTO dto) {
		Payment payment = selectPaymentType(dto);
		return mapper.map(service.save(payment), PaymentDTO.class);
	}

	private Payment selectPaymentType(PaymentDTO dto) {
		Payment payment = null;
		if(dto.getPaymentType().equals(PaymentType.PAYMENT_SLIP)) {
			payment = mapper.map(dto, PaymentSlip.class);
		} else {
			payment = mapper.map(dto, PaymentCard.class);
		}
		return payment;
	}
}
