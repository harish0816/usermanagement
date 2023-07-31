package com.demo.api.usermanagement.exceptions;

public class UserDoesNotExistException extends RuntimeException {

	private String message;

	public UserDoesNotExistException() {
	}

	public UserDoesNotExistException(String msg) {
		super(msg);
		this.message = msg;
	}

}
