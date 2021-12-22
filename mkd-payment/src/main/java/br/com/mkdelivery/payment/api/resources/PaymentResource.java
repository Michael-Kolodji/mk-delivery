package br.com.mkdelivery.payment.api.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.mkdelivery.payment.api.domain.models.Payment;
import br.com.mkdelivery.payment.api.dto.PaymentDTO;
import br.com.mkdelivery.payment.api.filter.PaymentFilter;
import br.com.mkdelivery.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentResource {

	private final PaymentService service;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public PaymentDTO save(@RequestBody @Valid PaymentDTO dto) {
		Payment payment = dto.convertToPayment(dto);
		return dto.convertToDTO(service.save(payment));
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public PaymentDTO findById(@PathVariable String id) {
		return PaymentDTO.getPaymentDto(service.findByUuid(id));
	}
	
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public Page<PaymentDTO> findByType(PaymentFilter filter, Pageable pageable) {
		Payment payment = new ModelMapper().map(filter, Payment.class);
		Page<Payment> result = service.findByFilter(payment, pageable);
		
		List<PaymentDTO> payments = result.getContent()
				.stream()
				.map(PaymentDTO::getPaymentDto)
				.collect(Collectors.toList());
		
		return new PageImpl<>(payments, pageable, result.getTotalElements());
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public PaymentDTO chargebackPayment(@PathVariable String id) {
		return PaymentDTO.getPaymentDto(service.chargeback(id));
	}

}
