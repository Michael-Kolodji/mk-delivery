package br.com.mkdelivery.payment.service.validadtors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.mkdelivery.payment.api.domain.models.PaymentCreditCard;
import br.com.mkdelivery.payment.service.validators.CreditCardValidator;
import br.com.mkdelivery.payment.util.UtilPayment;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class CreditCardValidatorTest {

	@Test
	@DisplayName("Should validate credit card numbers")
	void validateCreditCardNumber() {
		var isValidCreditCard = new AtomicBoolean(true);
		UtilPayment.creditCardNummbers().forEach((key, value) -> {
			PaymentCreditCard payment = UtilPayment.paymentCreditCard();
			payment.setCardNumber(value);
			
			if(!new CreditCardValidator().isValidCreditCard(payment)) {
				isValidCreditCard.set(false);			
			}
		});
		
		assertTrue(isValidCreditCard.get());
	}
	
	@Test
	@DisplayName("Should throw exception when create a payment with invalid credit card number")
	void invalidCreditCardNumber() {
		var paymentCard = UtilPayment.paymentCreditCard();
		
		List<String> numbers = Arrays
				.asList("45566546464665645", "4556 6546 4646 65645", "4556 6546 4646 656", "45566546464665");
		
		List<Boolean> validations = new ArrayList<>();
		
		numbers.forEach(number -> {
			paymentCard.setCardNumber(number);
			validations.add(new CreditCardValidator().isValidCreditCard(paymentCard));
		});
		
		assertFalse(validations.contains(true));
	}
	
	@Test
	@DisplayName("Should create a payment with valid credit card cvv")
	void validCreditCardCVV() {
		var paymentCard = UtilPayment.paymentCreditCard();
		
		List<String> cvvs = Arrays.asList("111", "2222", "333", "4444");
		List<Boolean> validations = new ArrayList<>();
		
		cvvs.forEach(cvv -> {
			paymentCard.setCvv(cvv);
			validations.add(new CreditCardValidator().isValidCreditCard(paymentCard));
		});
		
		assertFalse(validations.contains(false));
	}
	
	@Test
	@DisplayName("Should throw exception when create a payment with invalid credit card cvv")
	void invalidCreditCardCVV() {
		var paymentCard = UtilPayment.paymentCreditCard();
		
		List<String> cvvs = Arrays.asList("A11", "2b2", "33c", "d444", "5E55", "666F", "77", "88888");
		List<Boolean> validations = new ArrayList<>();
		
		cvvs.forEach(cvv -> {
			paymentCard.setCvv(cvv);
			validations.add(new CreditCardValidator().isValidCreditCard(paymentCard));
		});
		
		assertFalse(validations.contains(true));
	}

	@Test
	@DisplayName("Should create a payment with valid credit card expiration date")
	void validCreditCardExpirationDate() {
		var paymentCard = UtilPayment.paymentCreditCard();
		paymentCard.setExpirationDate(LocalDate.of(LocalDate.now().plusYears(1).getYear(), Month.DECEMBER, 31));
		
		assertTrue(new CreditCardValidator().isValidCreditCard(paymentCard));
	}
	
	@Test
	@DisplayName("Should throw exception when create a payment with invalid credit card expiration date")
	void invalidCreditCardExpirationDate() {
		var paymentCard = UtilPayment.paymentCreditCard();
		paymentCard.setExpirationDate(LocalDate.of(
				LocalDate.now().getYear(), 
				LocalDate.now().getMonth(), 
				LocalDate.now().minusDays(1).getDayOfMonth()));
		
		assertFalse(new CreditCardValidator().isValidCreditCard(paymentCard));
	}
	
}
