package com.lnu.RESTfulCafe.controller.error;

public class ResourceIDNotFoundException extends RuntimeException {
    public ResourceIDNotFoundException(Long id) {
        super("Could not find resource with ID: " + id);
    }
}
