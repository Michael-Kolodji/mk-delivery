package br.com.mkdelivery.payment.util;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;

import br.com.mkdelivery.payment.api.domain.enums.PaymentStatus;
import br.com.mkdelivery.payment.api.domain.enums.PaymentType;
import br.com.mkdelivery.payment.api.domain.models.PaymentCreditCard;
import br.com.mkdelivery.payment.api.domain.models.PaymentSlip;
import br.com.mkdelivery.payment.api.dto.PaymentCreditCardDTO;
import br.com.mkdelivery.payment.api.dto.PaymentSlipDTO;

public final class UtilPayment {

	public static PaymentSlip paymentSlip() {
		return PaymentSlip
				.builder()
				.uuid("a0d7a0bf-ac09-4af6-b56f-13c1277a6b52")
				.type(PaymentType.PAYMENT_SLIP)
				.status(PaymentStatus.RECEBIDO)
				.barCode("456456465489654654564654")
				.dueDate(LocalDate.now())
				.build();
	}

	public static PaymentCreditCard paymentCreditCard() {
		return PaymentCreditCard
				.builder()
				.uuid("a0d7a0bf-ac09-4af6-b56f-13c1277a6b53")
				.type(PaymentType.PAYMENT_CREDIT_CARD)
				.status(PaymentStatus.RECEBIDO)
//				.cardNumber("1111 2222 3333 4444")
				.cardNumber("5106 4586 6759 4743")
				.cvv("111")
				.cardHolder("Jaspion")
				.expirationDate(LocalDate.of(LocalDate.now().plusYears(1).getYear(), Month.SEPTEMBER, 10))
				.build();
	}
	
	public static PaymentSlipDTO paymentSlipDTO() {
		return PaymentSlipDTO
				.builder()
				.uuid("a0d7a0bf-ac09-4af6-b56f-13c1277a6b52")
				.barCode("456456465489654654564654")
				.dueDate(LocalDate.now())
				.build();
	}

	public static PaymentCreditCardDTO paymentCardDTO() {
		return PaymentCreditCardDTO
				.builder()
				.uuid("a0d7a0bf-ac09-4af6-b56f-13c1277a6b53")
				.cardNumber("1111 2222 3333 4444")
				.cvv("111")
				.cardHolder("Jaspion")
				.expirationDate(LocalDate.of(2023, Month.SEPTEMBER, 10))
				.build();
	}
	
	public static HashMap<String,String> creditCardNummbers() {
		HashMap<String, String> cardNumbers = new HashMap<>();
		
		cardNumbers.put("visa", "4485 0599 0386 6541");
		cardNumbers.put("mastercard", "5106 4586 6759 4743");
		cardNumbers.put("amex", "3768 554509 24033");
		cardNumbers.put("discover", "6011 1919 9247 0931");
		cardNumbers.put("diners_club", "3010 714923 4533");
		cardNumbers.put("jcb", "3561 7195 4917 0488");
		
		return cardNumbers;
	}

}
