package com.demo.api.usermanagement.model;

import java.time.LocalDateTime;

/**
 * Instance that maps internal data to external data
 */
public class UserDTO {

	private String email;
	private String name;
	private LocalDateTime lastLoginTime;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(LocalDateTime lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

}
