package com.lnu.RESTfulCafe.controller.error;

public class BeanNotFoundException extends RuntimeException {
    public BeanNotFoundException(Long id) {
        super("Could not find bean with ID: " + id);
    }
}
