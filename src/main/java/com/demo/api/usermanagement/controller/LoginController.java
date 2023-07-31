package com.demo.api.usermanagement.controller;

import static com.demo.api.usermanagement.constants.MessageConstants.BAD_CREDENTIALS;
import static com.demo.api.usermanagement.constants.MessageConstants.LOGIN_SUCCESSFUL;
import static com.demo.api.usermanagement.constants.MessageConstants.USER_DOES_NOT_EXIST;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.api.usermanagement.exceptions.BadCredentialsException;
import com.demo.api.usermanagement.exceptions.UserDoesNotExistException;
import com.demo.api.usermanagement.model.LoginDetails;
import com.demo.api.usermanagement.model.UserDetails;
import com.demo.api.usermanagement.service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * API to validate User credentials
 */
@RestController
@AllArgsConstructor
@RequestMapping("usermanagement/login")
public class LoginController {

	/**
	 * Service to fetch user data
	 * 
	 */
	private UserService userService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Validate user. For now using users table. We can improve by having separate
	 * table for Login
	 * 
	 * @param user - Request body
	 * @return 201
	 */
	@PostMapping
	public ResponseEntity<String> validateUser(@Valid @RequestBody LoginDetails request) {

		UserDetails savedUser = userService.getUserById(request.getUserId());
		if (savedUser == null) {
			throw new UserDoesNotExistException(String.format(USER_DOES_NOT_EXIST, request.getUserId()));

		}
		if (!passwordEncoder().matches(request.getPassword(), savedUser.getPassword())) {
			throw new BadCredentialsException(String.format(BAD_CREDENTIALS, request.getUserId()));
		}

		savedUser.setLastLoginTime(LocalDateTime.now());
		userService.updateUser(savedUser, true);

		return new ResponseEntity<>(LOGIN_SUCCESSFUL, HttpStatus.OK);
	}
}
