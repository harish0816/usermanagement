package com.demo.api.usermanagement.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.api.usermanagement.model.LoginRequest;
import com.demo.api.usermanagement.model.User;
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

	private static Logger LOG = LoggerFactory.getLogger(UserController.class);

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
	 * Validate user
	 * 
	 * @param user    - Request body
	 * @param headers - Request headers
	 * @return 201 if successful or 500 if failed
	 */
	@PostMapping
	public ResponseEntity<String> validateUser(@Valid @RequestBody LoginRequest request,
			@RequestHeader Map<String, String> headers) {

		try {
			User savedUser = userService.getUserById(request.getUserId());
			if (!passwordEncoder().matches(request.getPassword(), savedUser.getPassword())) {
				return new ResponseEntity<>("Bad credentials", HttpStatus.UNAUTHORIZED);
			}

			savedUser.setLastLoginTime(LocalDateTime.now());
			userService.updateUser(savedUser);

			return new ResponseEntity<>("Login successful", HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Failed during user validation: {} ", e);
			return new ResponseEntity<>("Something went wrong during user validation",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
