package com.lnu.RESTfulCafe.model.subscriber;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "seq", initialValue = 1)
public class Subscriber {
    private @Id @GeneratedValue(generator = "seq") Long id;
    private String url;
    private Event event;

    public Subscriber() {}

    public Subscriber(Long id, String url, Event event) {
        this.id = id;
        this.url = url;
        this.event = event;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscriber that = (Subscriber) o;
        return Objects.equals(id, that.id) && Objects.equals(url, that.url) && event == that.event;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, event);
    }
}
