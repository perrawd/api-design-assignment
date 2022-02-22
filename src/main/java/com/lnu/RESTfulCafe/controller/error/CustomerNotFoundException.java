package com.lnu.RESTfulCafe.controller.error;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Long id) {
        super("Could not find customer with ID: " + id);
    }
}
