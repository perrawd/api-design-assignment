package com.lnu.RESTfulCafe.event.drink;

import com.lnu.RESTfulCafe.event.error.ResponseException;
import com.lnu.RESTfulCafe.model.drink.Drink;

import com.lnu.RESTfulCafe.model.subscriber.Event;
import com.lnu.RESTfulCafe.model.subscriber.Subscriber;
import com.lnu.RESTfulCafe.model.subscriber.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class DrinkAddedEventHandler {
    @Autowired
    private SubscriberRepository repository;

    @EventListener
    public void handleEvent (DrinkAddedEvent event) {
        List<Subscriber> subscribers = repository.findAll();

        WebClient webClient = WebClient.create();

        subscribers
                .stream()
                .filter(subscriber -> subscriber.getEvent() == Event.NEWDRINK)
                .forEach(subscriber -> {

                    webClient.post()
                            .uri(subscriber.getUrl())
                            .header("x-app-secret", subscriber.getSecret())
                            .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                            .body(Mono.just(event.getDrink()), Drink.class)
                            .retrieve()
                            .onStatus(HttpStatus::is4xxClientError, response -> {
                                return Mono.error(ResponseException::new);
                            })
                            .onStatus(HttpStatus::is5xxServerError, response -> {
                                return Mono.error(ResponseException::new);
                            })
                            .bodyToMono(Void.class)
                            .subscribe();
                });
    }
}
