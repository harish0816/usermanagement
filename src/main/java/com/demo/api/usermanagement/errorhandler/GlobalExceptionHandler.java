package com.demo.api.usermanagement.errorhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.demo.api.usermanagement.exceptions.BadCredentialsException;
import com.demo.api.usermanagement.exceptions.UserAlreadyExistException;
import com.demo.api.usermanagement.exceptions.UserDoesNotExistException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = UserAlreadyExistException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public @ResponseBody ErrorResponse handleException(UserAlreadyExistException ex) {
		return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
	}

	@ExceptionHandler(value = UserDoesNotExistException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody ErrorResponse handleException(UserDoesNotExistException ex) {
		return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
	}

	@ExceptionHandler(value = BadCredentialsException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public @ResponseBody ErrorResponse handleException(BadCredentialsException ex) {
		return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
	}

}
