package com.demo.api.usermanagement.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.demo.api.usermanagement.model.User;
import com.demo.api.usermanagement.model.UserDTO;
import com.demo.api.usermanagement.repository.UserRepository;
import com.demo.api.usermanagement.service.UserService;

import lombok.AllArgsConstructor;

/**
 * Implementation for CURD operations for User entity
 */

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	@Override
	public User createUser(User user) {

		return userRepository.save(user);
	}

	@Override
	public List<UserDTO> getAllUsers() {

		List<User> list = userRepository.findAll();
		return list.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public User updateUser(User user) {

		User existingUser = userRepository.findById(user.getEmail()).get();
		existingUser.setName(user.getName());
		existingUser.setPassword(user.getPassword());

		User updatedUser = userRepository.save(existingUser);

		return updatedUser;
	}

	@Override
	public void deleteUser(String userId) {

		userRepository.deleteById(userId);
	}

	@Override
	public User getUserById(String email) {

		Optional<User> optionalUser = userRepository.findById(email);
		return optionalUser.isPresent() ? optionalUser.get() : null;
	}

	private UserDTO convertToDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setName(user.getName());
		userDTO.setEmail(user.getEmail());
		userDTO.setLastLoginTime(user.getLastLoginTime());
		return userDTO;
	}
}
