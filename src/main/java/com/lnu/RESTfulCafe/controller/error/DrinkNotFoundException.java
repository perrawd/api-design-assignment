package com.lnu.RESTfulCafe.controller.error;

public class DrinkNotFoundException extends RuntimeException {
    public DrinkNotFoundException(Long id) {
        super("Could not find drink with ID: " + id);
    }
}
