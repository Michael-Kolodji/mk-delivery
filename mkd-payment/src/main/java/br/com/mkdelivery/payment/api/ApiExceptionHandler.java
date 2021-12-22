package br.com.mkdelivery.payment.api;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.mkdelivery.payment.api.exception.ApiMessageError;
import br.com.mkdelivery.payment.exception.BusinessException;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(NOT_FOUND)
	public ApiMessageError entityNotFoundException(EntityNotFoundException e) {
		return new ApiMessageError(BAD_REQUEST.name(), BAD_REQUEST.value(), e.getMessage());
	}
	
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

	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(BAD_REQUEST)
	public ApiMessageError businessException(BusinessException e) {
		return new ApiMessageError(BAD_REQUEST.name(), BAD_REQUEST.value(), e.getMessage());
	}

	
}
