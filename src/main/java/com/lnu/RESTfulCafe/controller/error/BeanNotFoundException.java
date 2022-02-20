package com.lnu.RESTfulCafe.controller.error;

public class BeanNotFoundException extends RuntimeException {
    public BeanNotFoundException(String name) {
        super("Could not find bean with name: " + name);
    }
}
