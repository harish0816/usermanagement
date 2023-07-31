package com.demo.api.usermanagement.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.api.usermanagement.model.User;
import com.demo.api.usermanagement.model.UserDTO;
import com.demo.api.usermanagement.service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * Controller that handles user management requests
 */
@RestController
@AllArgsConstructor
@RequestMapping("usermanagement/users")
public class UserController {

	private static Logger LOG = LoggerFactory.getLogger(UserController.class);

	/**
	 * Service that handles CURD operations
	 * 
	 */
	private UserService userService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Create new user
	 * 
	 * @param user    - Request body
	 * @param headers - Request headers
	 * @return 201 if successful or 500 if failed
	 */
	@PostMapping
	public ResponseEntity<String> createUser(@Valid @RequestBody User user,
			@RequestHeader Map<String, String> headers) {
		try {
			String pswd = passwordEncoder().encode(user.getPassword());
			user.setPassword(pswd);
			User savedUser = userService.createUser(user);
			return new ResponseEntity<>("successfully created account for user " + savedUser.getEmail(),
					HttpStatus.CREATED);
		} catch (Exception e) {
			LOG.error("Failed during user creation: {} ", e);
			return new ResponseEntity<>("Something went wrong during user creation", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * List available users
	 *
	 * @return List of Users
	 */
	@GetMapping
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		try {
			List<UserDTO> users = userService.getAllUsers();
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Failed while fetching user info: {} ", e);
			return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Update User info
	 *
	 * @param email  - userId
	 * @param header - Request headers
	 * @param user   - request body
	 *
	 * @return 200 if successful or 500 if failed
	 */
	@PutMapping("{email}")
	public ResponseEntity<String> updateUser(@PathVariable("email") String email,
			@RequestHeader(value = "UserManagement-Version", required = false) String header, @RequestBody User user) {
		try {
			String pswd = passwordEncoder().encode(user.getPassword());
			user.setPassword(pswd);
			User updatedUser = userService.updateUser(user);
			return new ResponseEntity<>("successfully updated account for user " + updatedUser.getEmail(),
					HttpStatus.OK);
		} catch (Exception e) {
			LOG.error("Failed during user update: {} ", e);
			return new ResponseEntity<>("User update failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * @param userId - userId to delete
	 * @param header - Request header
	 *
	 * @return 204 if successful or 500 if failed
	 */
	@DeleteMapping("{email}")
	public ResponseEntity<String> deleteUser(@PathVariable("email") String userId,
			@RequestHeader(value = "UserManagement-Version", required = false) String header) {
		try {
			userService.deleteUser(userId);
			return new ResponseEntity<>("User " + userId + " successfully deleted!", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			LOG.error("Failed while deleting user data: {} ", e);
			return new ResponseEntity<>("Failed to delete user", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
