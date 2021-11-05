package br.com.mkdelivery.payment.api.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ApiMessageError {

	private String message;
	private Integer code;
	private List<String> errors;
	
	public ApiMessageError(String name, int value, BindingResult result) {
		this.message = name;
		this.code = value;
		this.errors = new ArrayList<>();
	    
		result.getAllErrors()
			.forEach(error -> {
				String field = ((FieldError) error).getField();
				String errorMessage = error.getDefaultMessage(); 
				this.errors.add(field.concat(": ").concat(errorMessage));
			});
	}
	
	public String getMessage() {
		return message;
	}
	
	public Integer getCode() {
		return code;
	}
	
	public List<String> getErrors() {
		return errors;
	}

}
