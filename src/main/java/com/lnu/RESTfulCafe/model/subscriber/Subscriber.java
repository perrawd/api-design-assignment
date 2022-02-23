package com.lnu.RESTfulCafe.model.subscriber;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "subscriberseq", initialValue = 1, allocationSize=1)
public class Subscriber {
    private @Id @GeneratedValue(generator = "subscriberseq") Long id;
    private String url;
    private Event event;
    private @JsonIgnore String secret;

    public Subscriber() {}

    public Subscriber(Long id, String url, Event event, String secret) {
        this.id = id;
        this.url = url;
        this.event = event;
        this.secret = secret;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @JsonIgnore
    public String getSecret() {
        return secret;
    }

    @JsonProperty
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscriber that = (Subscriber) o;
        return Objects.equals(id, that.id) && Objects.equals(url, that.url) && event == that.event && Objects.equals(secret, that.secret);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, event, secret);
    }
}
