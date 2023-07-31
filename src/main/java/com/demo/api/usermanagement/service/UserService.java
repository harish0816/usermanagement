package com.demo.api.usermanagement.service;

import java.util.List;

import com.demo.api.usermanagement.model.User;
import com.demo.api.usermanagement.model.UserDTO;

/**
 * Service for CURD operations on User entity
 */
public interface UserService {

	User createUser(User user);

	List<UserDTO> getAllUsers();

	User updateUser(User user);

	User getUserById(String email);

	void deleteUser(String email);
}
