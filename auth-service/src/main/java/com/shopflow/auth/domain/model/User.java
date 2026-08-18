package com.shopflow.auth.domain.model;

import com.shopflow.auth.domain.enums.Role;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class User {

    private UUID id;
    private String name;
    private String email;
    private String password;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(
            UUID id,
            String name,
            String email,
            String password,
            Role role,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
