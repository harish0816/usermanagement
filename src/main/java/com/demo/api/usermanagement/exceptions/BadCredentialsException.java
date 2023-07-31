package com.demo.api.usermanagement.exceptions;

public class BadCredentialsException extends RuntimeException {

	private String message;

	public BadCredentialsException() {
	}

	public BadCredentialsException(String msg) {
		super(msg);
		this.message = msg;
	}

}
