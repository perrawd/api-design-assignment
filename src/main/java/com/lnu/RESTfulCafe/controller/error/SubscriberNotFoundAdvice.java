package com.lnu.RESTfulCafe.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class SubscriberNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(SubscriberNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String SubscriberNotFoundHandler(SubscriberNotFoundException ex) {
        return ex.getMessage();
    }
}
