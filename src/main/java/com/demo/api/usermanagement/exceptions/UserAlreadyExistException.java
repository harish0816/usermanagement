package com.demo.api.usermanagement.exceptions;

public class UserAlreadyExistException extends RuntimeException {

	private String message;

	public UserAlreadyExistException() {
	}

	public UserAlreadyExistException(String msg) {
		super(msg);
		this.message = msg;
	}
}
