package com.lnu.RESTfulCafe.event.bean;

import com.lnu.RESTfulCafe.model.bean.Bean;
import org.springframework.context.ApplicationEvent;
import org.springframework.hateoas.EntityModel;

public class BeanAddedEvent extends ApplicationEvent {
    private final EntityModel<Bean> bean;

    BeanAddedEvent(Object source, EntityModel<Bean> bean) {
        super(source);
        this.bean = bean;
    }

    public EntityModel<Bean> getBean() {
        return bean;
    }
}
