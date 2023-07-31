package com.demo.api.usermanagement.errorhandler;

import java.net.ConnectException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
@ResponseBody
public class DatabaseExceptionHandler {

	@ExceptionHandler(value = { CannotCreateTransactionException.class })
	public ResponseEntity<?> cannotCreateTransactionException(CannotCreateTransactionException exception,
			WebRequest request) {
		if (exception.contains(ConnectException.class)) {
			log.error("DB ConnectException :  {}", exception.getMessage());
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
