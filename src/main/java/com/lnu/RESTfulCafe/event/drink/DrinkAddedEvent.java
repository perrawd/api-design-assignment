package com.lnu.RESTfulCafe.event.drink;

import com.lnu.RESTfulCafe.model.drink.Drink;
import org.springframework.context.ApplicationEvent;

public class DrinkAddedEvent extends ApplicationEvent {
    private final Drink drink;

    DrinkAddedEvent(Object source, Drink drink) {
        super(source);
        this.drink = drink;
    }

    public Drink getDrink() {
        return drink;
    }
}