package com.shopflow.auth.application.dto;

public record CreateUserRequest (
        String name,
        String email,
        String password
){
}
