package com.example.vyrwu.agillicDemoMvn.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// If user was not found, throw generic RuntimeException with description
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String userId) {
        super("User under ID: " + userId + " cannot be found.");
    }
}
