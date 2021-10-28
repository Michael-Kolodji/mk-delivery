package br.com.mkdelivery.payment.util;

import java.time.LocalDate;

import br.com.mkdelivery.payment.api.domain.enums.PaymentType;
import br.com.mkdelivery.payment.api.domain.enums.StatusPagamento;
import br.com.mkdelivery.payment.api.domain.models.PaymentSlip;
import br.com.mkdelivery.payment.api.dto.PaymentDTO;

public final class UtilPayment {

	public static PaymentSlip paymentSlip() {
		return PaymentSlip
				.builder()
				.uuid("a0d7a0bf-ac09-4af6-b56f-13c1277a6b52")
				.statusPagamento(StatusPagamento.RECEBIDO)
				.barCode("456456465489654654564654")
				.dueDate(LocalDate.now())
				.build();
	}

	public static PaymentDTO paymentSlipDTO() {
		return PaymentDTO
				.builder()
				.uuid("a0d7a0bf-ac09-4af6-b56f-13c1277a6b52")
				.statusPagamento(StatusPagamento.RECEBIDO)
				.barCode("456456465489654654564654")
				.dueDate(LocalDate.now())
				.paymentType(PaymentType.PAYMENT_SLIP)
				.build();
	}
}
