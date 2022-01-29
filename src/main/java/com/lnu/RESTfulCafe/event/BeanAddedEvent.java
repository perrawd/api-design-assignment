package com.lnu.RESTfulCafe.event;

import com.lnu.RESTfulCafe.model.bean.Bean;
import org.springframework.context.ApplicationEvent;

public class BeanAddedEvent extends ApplicationEvent {
    private final Bean bean;

    BeanAddedEvent(Object source, Bean bean) {
        super(source);
        this.bean = bean;
    }

    public Bean getBean() {
        return bean;
    }
}
