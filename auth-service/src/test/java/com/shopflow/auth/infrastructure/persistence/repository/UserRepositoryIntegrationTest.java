package com.shopflow.auth.infrastructure.persistence.repository;

import com.shopflow.auth.domain.enums.Role;
import com.shopflow.auth.domain.model.User;
import com.shopflow.auth.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest
class UserRepositoryIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16-alpine")
                    .withDatabaseName("shopflow_auth")
                    .withUsername("shopflow")
                    .withPassword("shopflow");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveUser() {
        User user = new User(
                null,
                "Matheus",
                "teste@shopflow.com",
                "hashed-password",
                Role.USER,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        User savedUser = userRepository.save(user);

        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getName()).isEqualTo("Matheus");
        assertThat(savedUser.getEmail()).isEqualTo("teste@shopflow.com");
        assertThat(savedUser.getRole()).isEqualTo(Role.USER);
    }

    @Test
    void shouldFindUserById(){
        User user = new User(
                null,
                "Matheus",
                "testeFindUserById@shopflow.com",
                "hashed-password",
                Role.USER,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        User savedUser = userRepository.save(user);

        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getId()).isEqualTo(savedUser.getId());
        assertThat(foundUser.get().getName()).isEqualTo("Matheus");
        assertThat(foundUser.get().getEmail()).isEqualTo("testeFindUserById@shopflow.com");
    }

    @Test
    void shouldFindUserByEmail(){
        User user = new User(
                null,
                "Matheus",
                "testeFindUserByEmail@shopflow.com",
                "hashed-password",
                Role.USER,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        User savedUser = userRepository.save(user);

        Optional<User> foundUser = userRepository.findByEmail("testeFindUserByEmail@shopflow.com");

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo("Matheus");
        assertThat(foundUser.get().getEmail()).isEqualTo("testeFindUserByEmail@shopflow.com");
    }

    @Test
    void shouldReturnEmptyEmailDoesNotExists(){

        Optional<User> foundUser = userRepository.findByEmail("naoexiste@shopflow.com");

        assertThat(foundUser).isEmpty();
    }

    @Test
    void shouldReturnTrueWhenEmailExists() {
        User user = new User(
                null,
                "Matheus",
                "testeReturnTrueWhenEmailExists@shopflow.com",
                "hashed-password",
                Role.USER,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        userRepository.save(user);

        boolean exists = userRepository.existsByEmail("testeReturnTrueWhenEmailExists@shopflow.com");

        assertThat(exists).isTrue();
    }

    @Test
    void shouldReturnFalseWhenEmailDoesNotExists() {
        boolean exists = userRepository.existsByEmail("naoexiste@shopflow.com");

        assertThat(exists).isFalse();
    }
}
