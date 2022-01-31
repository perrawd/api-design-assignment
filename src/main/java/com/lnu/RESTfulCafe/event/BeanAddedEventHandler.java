package com.lnu.RESTfulCafe.event;

import com.lnu.RESTfulCafe.model.bean.Bean;

import com.lnu.RESTfulCafe.model.subscriber.Event;
import com.lnu.RESTfulCafe.model.subscriber.Subscriber;
import com.lnu.RESTfulCafe.model.subscriber.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class BeanAddedEventHandler {
    @Autowired
    private SubscriberRepository repository;

    @EventListener
    public void handleEvent (BeanAddedEvent event) {
        RestTemplate restTemplate = new RestTemplate();

        List<Subscriber> subscribers = repository.findAll();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Bean> entity = new HttpEntity<Bean>(event.getBean(),headers);

        subscribers
                .stream()
                .filter(subscriber -> subscriber.getEvent() == Event.NEWBEAN)
                .forEach(subscriber -> {
                    restTemplate.exchange(subscriber.getUrl(), HttpMethod.POST, entity, Bean.class);
                });
    }
}
