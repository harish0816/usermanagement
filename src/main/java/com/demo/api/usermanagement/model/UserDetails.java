package com.demo.api.usermanagement.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * User Entity
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserDetails {

	@Id
	@NotBlank
	private String email;

	@Column(nullable = false)
	@NotBlank
	private String name;

	@Column(nullable = false, unique = true)
	@NotBlank
	private String password;

	@Column
	private LocalDateTime lastLoginTime;
}
