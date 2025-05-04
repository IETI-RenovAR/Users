package org.adaschool.project;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.adaschool.project.dto.UserDTO;
import org.adaschool.project.exception.UserNotFoundException;
import org.adaschool.project.model.UserEntity;
import org.adaschool.project.repository.UserRepository;
import org.adaschool.project.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class UserEntityServiceTest {
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
        UserDTO userDTO = new UserDTO("IETI", "renovar@eci.com", "remodelaciones");
        UserEntity expectedUserEntity = new UserEntity(userDTO);
        when(userRepository.save(any(UserEntity.class))).thenReturn(expectedUserEntity);

        UserEntity savedUserEntity = userService.saveUser(userDTO);

        assertEquals(expectedUserEntity.getUsername(), savedUserEntity.getUsername());
        assertEquals(expectedUserEntity.getEmail(), savedUserEntity.getEmail());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    // Obtener un usuario por ID y validar que la respuesta es correcta
    @Test
    void testGetUserById() {
        String userId = "12345";
        UserEntity expectedUserEntity = new UserEntity(userId, "IETI", "renovar@eci.com", "remodelaciones");
        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUserEntity));

        UserEntity foundUserEntity = userService.getUserById(userId);

        assertEquals(expectedUserEntity.getId(), foundUserEntity.getId());
        assertEquals(expectedUserEntity.getUsername(), foundUserEntity.getUsername());
        assertEquals(expectedUserEntity.getEmail(), foundUserEntity.getEmail());
        verify(userRepository, times(1)).findById(userId);
    }

    // Intentar actualizar un usuario inexistente y verificar que se maneje adecuadamente el error
    @Test
    void testUpdateUserNotFound() {
        String userId = "12345";
        UserDTO userDTO = new UserDTO("IETIa", "renovar@eci.com", "remodelaciones");
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.updateUser(userId, userDTO);
        });
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(any(UserEntity.class));
    }
}
