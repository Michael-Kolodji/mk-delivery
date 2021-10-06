package br.com.mkdelivery.client.api;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import javax.persistence.EntityNotFoundException;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.mkdelivery.client.api.exception.ApiMessageError;
import br.com.mkdelivery.client.services.exception.BusinessException;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(BAD_REQUEST)
	public ApiMessageError methodArgumentNotValidException(MethodArgumentNotValidException e) {
		return new ApiMessageError(BAD_REQUEST.name(), BAD_REQUEST.value(), e.getBindingResult());
	}
	
	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(BAD_REQUEST)
	public ApiMessageError businessException(BusinessException e) {
		return new ApiMessageError(BAD_REQUEST.name(), BAD_REQUEST.value(), e.getMessage());
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(NOT_FOUND)
	public ApiMessageError responseStatusException(EntityNotFoundException e) {
		return new ApiMessageError(NOT_FOUND.name(), NOT_FOUND.value(), e.getMessage());
	}
	
}
