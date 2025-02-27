package org.adaschool.project;

import org.adaschool.project.controller.user.UserController;
import org.adaschool.project.dto.UserDTO;
import org.adaschool.project.exception.UserNotFoundException;
import org.adaschool.project.model.User;
import org.adaschool.project.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserControllerTest {
	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCreateUser() {
		UserDTO userDTO = new UserDTO("IETI", "PROYECTO", "renovar@eci.com", "remodelaciones");
		User createdUser = new User("1", "IETI", "PROYECTO", "renovar@eci.com", "remodelacionesEncode");

		when(userService.saveUser(any(UserDTO.class))).thenReturn(createdUser);

		ResponseEntity<User> response = userController.createUser(userDTO);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(URI.create("/v1/users/1"), response.getHeaders().getLocation());
		assertEquals(createdUser, response.getBody());
	}

	@Test
	public void testGetUserById() {
		String userId = "1";
		User user = new User(userId, "IETI", "PROYECTO", "renovar@eci.com", "remodelacionesEncode");

		when(userService.getUserById(userId)).thenReturn(user);

		ResponseEntity<User> response = userController.findById(userId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(user, response.getBody());
	}

	@Test
	public void testGetUserByIdNotFound() {
		String userId = "999";
		when(userService.getUserById(userId)).thenThrow(new UserNotFoundException(userId));

		assertThrows(UserNotFoundException.class, () -> {
			userController.findById(userId);
		});
	}

	@Test
	public void testUpdateUser() {
		String userId = "1";
		UserDTO userDTO = new UserDTO("IETIa", "PROYECTO", "renovarmal@eci.com", "newremodelaciones");
		User updatedUser = new User(userId, "IETIa", "PROYECTO", "renovarmal@eci.com", "remodelacionesEncode");

		when(userService.updateUser(userId, userDTO)).thenReturn(updatedUser);

		ResponseEntity<User> response = userController.updateUser(userId, userDTO);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(updatedUser, response.getBody());
	}

	@Test
	public void testDeleteUser() {
		String userId = "1";

		doNothing().when(userService).deleteUser(userId);

		ResponseEntity<Void> response = userController.deleteUser(userId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void testUpdateUserNotFound() {
		String userId = "999";
		UserDTO userDTO = new UserDTO("IETIa", "PROYECTO", "renovarmal@eci.com", "newremodelaciones");

		when(userService.updateUser(userId, userDTO)).thenThrow(new UserNotFoundException(userId));

		assertThrows(UserNotFoundException.class, () -> {
			userController.updateUser(userId, userDTO);
		});
	}
	@Test
	public void testDeleteUserNotFound() {
		String userId = "999";
		doThrow(new UserNotFoundException(userId)).when(userService).deleteUser(userId);

		assertThrows(UserNotFoundException.class, () -> {
			userController.deleteUser(userId);
		});
	}
}
