package br.com.mkdelivery.payment.service.validators;

import java.util.regex.Pattern;

import br.com.mkdelivery.payment.api.domain.models.Payment;
import br.com.mkdelivery.payment.api.domain.models.PaymentCreditCard;

public class CreditCardValidator {

	public static boolean isValidCreditCard(Payment payment) {
		var number = ((PaymentCreditCard) payment).getCardNumber();
		var value = number.replace(" ", "");

		if (!Pattern.matches("[0-9]+", value)) {
			return false;
		}

		var sum = 0;
		var shouldDouble = false;
		for (var i = value.length() - 1; i >= 0; i--) {
			var digit = (int) value.charAt(i);

			if (shouldDouble && (digit *= 2) > 9) {
				digit -= 9;
			}

			sum += digit;
			shouldDouble = !shouldDouble;
		}
		return (sum % 10) != 0;
	}
	
}
