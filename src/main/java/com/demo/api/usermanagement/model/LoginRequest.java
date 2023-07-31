package com.demo.api.usermanagement.model;

/**
 * Instance to hold login info
 */
public class LoginRequest {

	private String userId;
	private String password;

	public LoginRequest(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
