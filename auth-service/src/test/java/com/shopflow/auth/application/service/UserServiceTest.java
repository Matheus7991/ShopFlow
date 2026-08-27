package com.shopflow.auth.application.service;

import com.shopflow.auth.application.dto.CreateUserRequest;
import com.shopflow.auth.application.exception.EmailAlreadyExistsException;
import com.shopflow.auth.domain.enums.Role;
import com.shopflow.auth.domain.model.User;
import com.shopflow.auth.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldCreateUser() {
        CreateUserRequest request = new CreateUserRequest(
                "Matheus",
                "testeCreateUser@gmail.com",
                "password"
        );

        User savedUser = new User(
                null,
                "Matheus",
                "testeCreateUser@gmail.com",
                "password",
                Role.USER,
                null,
                null
        );

        when(userRepository.existsByEmail(request.email())).thenReturn(false);

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.createUser(request);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Matheus");
        assertThat(result.getEmail()).isEqualTo("testeCreateUser@gmail.com");
        assertThat(result.getRole()).isEqualTo(Role.USER);

        verify(userRepository).existsByEmail(request.email());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldNotCreateUserWhenEmailAlreadyExists() {
        CreateUserRequest request = new CreateUserRequest(
                "Matheus",
                "testeNotCreateUserWhenEmailAlreadyExists",
                "password"
        );

        when(userRepository.existsByEmail(request.email())).thenReturn(true);

        assertThatThrownBy(() -> userService.createUser(request)).isInstanceOf(EmailAlreadyExistsException.class);

        verify(userRepository).existsByEmail(request.email());
        verify(userRepository, never()).save(any(User.class));
    }
}
