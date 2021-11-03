package br.com.mkdelivery.payment.util;

import java.time.LocalDate;
import java.time.Month;

import br.com.mkdelivery.payment.api.domain.enums.StatusPagamento;
import br.com.mkdelivery.payment.api.domain.models.Payment;
import br.com.mkdelivery.payment.api.domain.models.PaymentCard;
import br.com.mkdelivery.payment.api.domain.models.PaymentSlip;
import br.com.mkdelivery.payment.api.dto.PaymentCardDTO;
import br.com.mkdelivery.payment.api.dto.PaymentDTO;
import br.com.mkdelivery.payment.api.dto.PaymentSlipDTO;

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

	public static Payment paymentCard() {
		return PaymentCard
				.builder()
				.uuid("a0d7a0bf-ac09-4af6-b56f-13c1277a6b53")
				.statusPagamento(StatusPagamento.RECEBIDO)
				.cardNumber("1111 2222 3333 4444")
				.cvv("111")
				.cardHolder("Jaspion")
				.expirationDate(LocalDate.of(2023, Month.SEPTEMBER, 10))
				.build();
	}
	
	public static PaymentDTO paymentSlipDTO() {
		return PaymentSlipDTO
				.builder()
				.uuid("a0d7a0bf-ac09-4af6-b56f-13c1277a6b52")
				.statusPagamento(StatusPagamento.RECEBIDO)
				.barCode("456456465489654654564654")
				.dueDate(LocalDate.now())
				.build();
	}

	public static PaymentDTO paymentCardDTO() {
		return PaymentCardDTO
				.builder()
				.uuid("a0d7a0bf-ac09-4af6-b56f-13c1277a6b53")
				.statusPagamento(StatusPagamento.RECEBIDO)
				.cardNumber("1111 2222 3333 4444")
				.cvv("111")
				.cardHolder("Jaspion")
				.expirationDate(LocalDate.of(2023, Month.SEPTEMBER, 10))
				.build();
	}

}
