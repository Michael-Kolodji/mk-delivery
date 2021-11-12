package br.com.mkdelivery.payment.util;

import java.time.LocalDate;
import java.time.Month;

import br.com.mkdelivery.payment.api.domain.enums.StatusPagamento;
import br.com.mkdelivery.payment.api.domain.models.PaymentCreditCard;
import br.com.mkdelivery.payment.api.domain.models.PaymentSlip;
import br.com.mkdelivery.payment.api.dto.PaymentCreditCardDTO;
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

	public static PaymentCreditCard paymentCreditCard() {
		return PaymentCreditCard
				.builder()
				.uuid("a0d7a0bf-ac09-4af6-b56f-13c1277a6b53")
				.statusPagamento(StatusPagamento.RECEBIDO)
				.cardNumber("1111 2222 3333 4444")
//				.cardNumber("5106 4586 6759 4743")
				.cvv("111")
				.cardHolder("Jaspion")
				.expirationDate(LocalDate.of(2023, Month.SEPTEMBER, 10))
				.build();
	}
	
	public static PaymentSlipDTO paymentSlipDTO() {
		return PaymentSlipDTO
				.builder()
				.uuid("a0d7a0bf-ac09-4af6-b56f-13c1277a6b52")
				.statusPagamento(StatusPagamento.RECEBIDO)
				.barCode("456456465489654654564654")
				.dueDate(LocalDate.now())
				.build();
	}

	public static PaymentCreditCardDTO paymentCardDTO() {
		return PaymentCreditCardDTO
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
