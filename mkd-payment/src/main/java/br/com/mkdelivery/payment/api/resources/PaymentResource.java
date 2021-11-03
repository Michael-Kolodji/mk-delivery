package br.com.mkdelivery.payment.api.resources;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.mkdelivery.payment.api.domain.models.Payment;
import br.com.mkdelivery.payment.api.dto.PaymentDTO;
import br.com.mkdelivery.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentResource {

	private final PaymentService service;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public PaymentDTO save(@RequestBody PaymentDTO dto) {
		Payment payment = dto.convertToPayment(dto);
		return dto.convertToDTO(service.save(payment));
	}

}
