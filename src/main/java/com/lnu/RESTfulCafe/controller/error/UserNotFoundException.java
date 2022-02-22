package com.lnu.RESTfulCafe.controller.error;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("Could not find employee with ID: " + id);
    }
}
