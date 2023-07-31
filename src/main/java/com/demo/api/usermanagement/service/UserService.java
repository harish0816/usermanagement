package com.demo.api.usermanagement.service;

import java.util.List;

import com.demo.api.usermanagement.model.UserDetails;
import com.demo.api.usermanagement.model.UserDetailsDTO;

/**
 * Service for CURD operations on User entity
 */
public interface UserService {

	UserDetails createUser(UserDetails user);

	List<UserDetailsDTO> getAllUsers();

	UserDetails updateUser(UserDetails user, boolean isLogin);

	UserDetails getUserById(String email);

	void deleteUser(String email);
}
