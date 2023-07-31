package com.demo.api.usermanagement.service.impl;

import static com.demo.api.usermanagement.constants.MessageConstants.USER_ALREAD_EXIST;
import static com.demo.api.usermanagement.constants.MessageConstants.USER_DOES_NOT_EXIST;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.demo.api.usermanagement.exceptions.UserAlreadyExistException;
import com.demo.api.usermanagement.exceptions.UserDoesNotExistException;
import com.demo.api.usermanagement.model.UserDetails;
import com.demo.api.usermanagement.model.UserDetailsDTO;
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
	public UserDetails createUser(UserDetails user) {
		UserDetails existingUser = getUserById(user.getEmail());
		if (existingUser != null) {
			throw new UserAlreadyExistException(String.format(USER_ALREAD_EXIST, user.getEmail()));
		}
		return userRepository.save(user);
	}

	@Override
	public List<UserDetailsDTO> getAllUsers() {

		List<UserDetails> list = userRepository.findAll();
		return list.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public UserDetails updateUser(UserDetails user, boolean isLogin) {

		if (isLogin) {
			return userRepository.save(user);
		} else {
			UserDetails existingUser = getUserById(user.getEmail());
			if (existingUser == null) {
				throw new UserDoesNotExistException(String.format(USER_DOES_NOT_EXIST, user.getEmail()));
			}

			existingUser.setName(user.getName());
			existingUser.setPassword(user.getPassword());
			return userRepository.save(user);
		}
	}

	@Override
	public void deleteUser(String userId) {

		userRepository.deleteById(userId);
	}

	@Override
	public UserDetails getUserById(String email) {

		Optional<UserDetails> optionalUser = userRepository.findById(email);
		return optionalUser.isPresent() ? optionalUser.get() : null;
	}

	private UserDetailsDTO convertToDTO(UserDetails user) {
		UserDetailsDTO userDTO = new UserDetailsDTO();
		userDTO.setName(user.getName());
		userDTO.setEmail(user.getEmail());
		userDTO.setLastLoginTime(user.getLastLoginTime());
		return userDTO;
	}
}
