package br.com.mkdelivery.payment.service.validators;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.mkdelivery.payment.api.domain.models.Payment;
import br.com.mkdelivery.payment.api.domain.models.PaymentCreditCard;

public class CreditCardValidator {

	public boolean isValidCreditCard(Payment payment) {
		PaymentCreditCard paymentCreditCard = (PaymentCreditCard) payment;
		return isAcceptedCreditCards(paymentCreditCard.getCardNumber()) 
				&& isValidCreditCardNumber(paymentCreditCard.getCardNumber())
				&& isValidateCVV(paymentCreditCard.getCvv())
				&& isValidateExpirationDate(paymentCreditCard.getExpirationDate());
	}
	
	private boolean isAcceptedCreditCards(String cardNumber) {
		var accepted = new AtomicBoolean(false);
		var number = cardNumber.replace(" ", "");
		  
		supportedFlags().forEach((key, value) -> {
			Pattern pattern = Pattern.compile(value);
			Matcher matcher = pattern.matcher(number);
			if (matcher.matches()) {
				accepted.set(true);
			}
		});
		  
		  return accepted.get();
	}
	
	private boolean isValidCreditCardNumber(String number) {
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
		return  (sum % 10) != 0;
	}
	
	private boolean isValidateCVV(String cvv) {
		cvv = cvv.replace(" ", "");
		if (!Pattern.matches("[0-9]+", cvv)) {
			return false;
		}
		return Pattern.matches("^\\d{4}$", cvv) || Pattern.matches("^\\d{3}$", cvv);
	}
	
	private boolean isValidateExpirationDate(LocalDate expirationDate) {
		return expirationDate.isAfter(LocalDate.now());
	}
	
	private Map<String, String> supportedFlags() {
		Map<String, String> supportedFlags = new HashMap<>();
		
		supportedFlags.put("visa", "^4[0-9]{12}(?:[0-9]{3})?$");
		supportedFlags.put("mastercard", "^5[1-5][0-9]{14}$|^2(?:2(?:2[1-9]|[3-9][0-9])|[3-6][0-9][0-9]|7(?:[01][0-9]|20))[0-9]{12}$");
		supportedFlags.put("amex", "^3[47][0-9]{13}$");
		supportedFlags.put("discover", "^65[4-9][0-9]{13}|64[4-9][0-9]{13}|6011[0-9]{12}|(622(?:12[6-9]|1[3-9][0-9]|[2-8][0-9][0-9]|9[01][0-9]|92[0-5])[0-9]{10})$");
		supportedFlags.put("diners_club", "^3(?:0[0-5]|[68][0-9])[0-9]{11}$");
		supportedFlags.put("jcb", "^(?:2131|1800|35[0-9]{3})[0-9]{11}$");
		
		return supportedFlags;
	}
	
}
