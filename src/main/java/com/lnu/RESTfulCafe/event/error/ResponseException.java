package com.lnu.RESTfulCafe.event.error;

public class ResponseException extends RuntimeException {
    public ResponseException() {
        super("An error occurred when trying to make a request to the resource");
    }
}
