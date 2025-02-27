package org.adaschool.project;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.adaschool.project.dto.UserDTO;
import org.adaschool.project.exception.UserNotFoundException;
import org.adaschool.project.model.User;
import org.adaschool.project.repository.UserRepository;
import org.adaschool.project.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Crear un usuario y verificar que se guarde correctamente en la base de datos
    @Test
    void testSaveUser() {
        UserDTO userDTO = new UserDTO("IETI", "PROYECTO", "renovar@eci.com", "remodelaciones");
        User expectedUser = new User(userDTO);
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        User savedUser = userService.saveUser(userDTO);

        assertEquals(expectedUser.getName(), savedUser.getName());
        assertEquals(expectedUser.getLastName(), savedUser.getLastName());
        assertEquals(expectedUser.getEmail(), savedUser.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    // Obtener un usuario por ID y validar que la respuesta es correcta
    @Test
    void testGetUserById() {
        String userId = "12345";
        User expectedUser = new User(userId, "IETI", "PROYECTO", "renovar@eci.com", "remodelaciones");
        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        User foundUser = userService.getUserById(userId);

        assertEquals(expectedUser.getId(), foundUser.getId());
        assertEquals(expectedUser.getName(), foundUser.getName());
        assertEquals(expectedUser.getLastName(), foundUser.getLastName());
        assertEquals(expectedUser.getEmail(), foundUser.getEmail());
        verify(userRepository, times(1)).findById(userId);
    }

    // Intentar actualizar un usuario inexistente y verificar que se maneje adecuadamente el error
    @Test
    void testUpdateUserNotFound() {
        String userId = "12345";
        UserDTO userDTO = new UserDTO("IETIa", "PROYECTO", "renovar@eci.com", "remodelaciones");
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.updateUser(userId, userDTO);
        });
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(any(User.class));
    }
}
