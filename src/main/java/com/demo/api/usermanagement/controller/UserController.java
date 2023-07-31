package com.demo.api.usermanagement.controller;

import static com.demo.api.usermanagement.constants.MessageConstants.USER_CREATED;
import static com.demo.api.usermanagement.constants.MessageConstants.USER_CREATION_FAILED;
import static com.demo.api.usermanagement.constants.MessageConstants.USER_UPDATED;
import static com.demo.api.usermanagement.constants.MessageConstants.USER_DELETED;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.api.usermanagement.model.UserDetails;
import com.demo.api.usermanagement.model.UserDetailsDTO;
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
	 * @param user - Request body
	 * @return 201 or 500 if failed
	 */
	@PostMapping
	public ResponseEntity<String> createUser(@Valid @RequestBody UserDetails user) {

		String pswd = passwordEncoder().encode(user.getPassword());
		user.setPassword(pswd);
		UserDetails savedUser = userService.createUser(user);
		if (savedUser != null) {
			return new ResponseEntity<>(USER_CREATED + savedUser.getEmail(), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(USER_CREATION_FAILED + user.getEmail(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * List available users
	 *
	 * @return List of Users
	 */
	@GetMapping
	public ResponseEntity<List<UserDetailsDTO>> getAllUsers() {

		List<UserDetailsDTO> users = userService.getAllUsers();
		// TODO Return with appropriate message for empty results
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	/**
	 * Update User info
	 *
	 * @param user - request body
	 *
	 * @return 200
	 */
	@PutMapping
	public ResponseEntity<String> updateUser(@RequestBody UserDetails user) {

		String pswd = passwordEncoder().encode(user.getPassword());
		user.setPassword(pswd);
		UserDetails updatedUser = userService.updateUser(user, false);
		return new ResponseEntity<>(USER_UPDATED + updatedUser.getEmail(), HttpStatus.OK);
	}

	/**
	 * @param userId - userId to delete
	 *
	 * @return 204
	 */
	@DeleteMapping("{email}")
	public ResponseEntity<String> deleteUser(@PathVariable("email") String userId) {

		userService.deleteUser(userId);
		return new ResponseEntity<>(String.format(USER_DELETED, userId), HttpStatus.NO_CONTENT);

	}
}
