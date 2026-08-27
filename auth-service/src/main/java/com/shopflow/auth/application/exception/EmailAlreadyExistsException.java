package com.shopflow.auth.application.exception;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String email) {
        super("Email already resgistered: " + email);
    }
}
