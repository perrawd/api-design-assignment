package com.lnu.RESTfulCafe.event.drink;

import com.lnu.RESTfulCafe.model.drink.Drink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

@Component
public class DrinkAddedEventPublisher {
    @Autowired
    private ApplicationEventPublisher publisher;

    DrinkAddedEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishEvent(EntityModel<Drink> drink) {
        publisher.publishEvent(new DrinkAddedEvent(this, drink));
    }
}
