package com.lnu.RESTfulCafe.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BeanAddedEventHandler {
    @EventListener
    public void handleEvent (BeanAddedEvent event) {
        System.out.println(event.getData());
    }
}
