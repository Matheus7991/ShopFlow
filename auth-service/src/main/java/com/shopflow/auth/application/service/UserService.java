package com.shopflow.auth.application.service;

import com.shopflow.auth.application.dto.CreateUserRequest;
import com.shopflow.auth.application.exception.EmailAlreadyExistsException;
import com.shopflow.auth.domain.enums.Role;
import com.shopflow.auth.domain.model.User;
import com.shopflow.auth.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(CreateUserRequest request){

        if(userRepository.existsByEmail(request.email())){
            throw new EmailAlreadyExistsException(request.email());
        }

        User user = new User(
                null,
                request.name(),
                request.email(),
                passwordEncoder.encode(request.password()),
                Role.USER,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        return userRepository.save(user);
    }
}
