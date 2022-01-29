package com.lnu.RESTfulCafe.event;

import com.lnu.RESTfulCafe.model.bean.Bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class BeanAddedEventHandler {

    @Value("${app.webhook.url}")
    private String postResource;

    @EventListener
    public String handleEvent (BeanAddedEvent event) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<Bean> entity = new HttpEntity<Bean>(event.getBean(),headers);

        return restTemplate.exchange(
                postResource, HttpMethod.POST, entity, String.class).getBody();
    }
}
