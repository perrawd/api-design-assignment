package com.lnu.RESTfulCafe.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class BeanNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler({BeanNotFoundException.class, ResourceIDNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String beanNotFoundHandler(RuntimeException ex) {
        return ex.getMessage();
    }
}
