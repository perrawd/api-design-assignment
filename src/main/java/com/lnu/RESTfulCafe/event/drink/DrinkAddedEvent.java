package com.lnu.RESTfulCafe.event.drink;

import com.lnu.RESTfulCafe.model.drink.Drink;
import org.springframework.context.ApplicationEvent;
import org.springframework.hateoas.EntityModel;

public class DrinkAddedEvent extends ApplicationEvent {
    private final EntityModel<Drink> drink;

    DrinkAddedEvent(Object source, EntityModel<Drink> drink) {
        super(source);
        this.drink = drink;
    }

    public EntityModel<Drink> getDrink() {
        return drink;
    }
}