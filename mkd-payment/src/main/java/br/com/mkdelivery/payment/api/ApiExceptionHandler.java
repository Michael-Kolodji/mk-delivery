package br.com.mkdelivery.payment.api;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.mkdelivery.payment.api.exception.ApiMessageError;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(BAD_REQUEST)
	public ApiMessageError methodArgumentNotValidException(MethodArgumentNotValidException e) {
		return new ApiMessageError(BAD_REQUEST.name(), BAD_REQUEST.value(), e.getBindingResult());
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(BAD_REQUEST)
	public ApiMessageError httpMessageNotReadableException(HttpMessageNotReadableException e) {
		return new ApiMessageError(BAD_REQUEST.name(), BAD_REQUEST.value(), e.getMessage());
	}
}
