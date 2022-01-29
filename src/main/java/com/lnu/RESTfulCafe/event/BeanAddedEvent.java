package com.lnu.RESTfulCafe.event;

import org.springframework.context.ApplicationEvent;

public class BeanAddedEvent extends ApplicationEvent {
    private final String data;

    BeanAddedEvent(Object source, String data) {
        super(source);
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
