package br.com.mkdelivery.client.api.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ApiMessageError {

	private String message;
	private Integer code;
	private List<String> errors;
	
	public ApiMessageError(String message, Integer code, BindingResult result) {
		
		this.message = message;
		this.code = code;
		this.errors = new ArrayList<>();
	    
		result.getAllErrors()
			.forEach(error -> {
				String field = ((FieldError) error).getField();
				String errorMessage = error.getDefaultMessage(); 
				this.errors.add(field.concat(": ").concat(errorMessage));
			});
	}
	
	public ApiMessageError(String message, int code, String error) {
		this.message = message;
		this.code = code;
		this.errors = Arrays.asList(error);
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
