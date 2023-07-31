package com.demo.api.usermanagement.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.demo.api.usermanagement.model.UserDetailsDTO;
import com.demo.api.usermanagement.service.UserService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(value = UserController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class })
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@Test
	public void testGetAllUsers() throws Exception {

		String emailId = "harish@gmail.com";
		UserDetailsDTO mockDto = new UserDetailsDTO();
		mockDto.setEmail(emailId);
		mockDto.setName("Harish");

		when(userService.getAllUsers()).thenReturn(Arrays.asList(mockDto));

		mockMvc.perform(MockMvcRequestBuilders.get("/usermanagement/users").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$[0].email").exists());
	}

	@Test
	public void testDeleteUser() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/usermanagement/users/harish@gmail.com"))
				.andExpect(status().isNoContent());
	}

	@Disabled("To be done")
	public void testCreateUser() {

	}

	@Disabled("To be done")
	public void testUpdateUser() {

	}

	@Disabled("To be done")
	public void testUserAlreadyExistsDuringCreate() {

	}

	@Disabled("To be done")
	public void testUserDoesNotExistsDuringUpdate() {

	}

	@Disabled("To be done")
	public void testInvalidLogin() {

	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

}
