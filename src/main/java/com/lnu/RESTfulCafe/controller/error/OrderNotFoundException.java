package com.lnu.RESTfulCafe.controller.error;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super("Could not find Order with ID: " + id);
    }
}