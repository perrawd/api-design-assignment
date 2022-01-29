package com.lnu.RESTfulCafe.controller.error;

public class SubscriberNotFoundException extends RuntimeException {
    public SubscriberNotFoundException(Long id) {
        super("Could not find subscriber with ID: " + id);
    }
}