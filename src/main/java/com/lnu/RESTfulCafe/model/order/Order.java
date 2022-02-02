package com.lnu.RESTfulCafe.model.order;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "CUSTOMER_ORDER")
public class Order {
    private @Id @GeneratedValue Long id;
    private Status status;
    private String special;

    Order() {}

    public Order(Status status, String special) {
        this.status = status;
        this.special = special;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && status == order.status && Objects.equals(special, order.special);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, special);
    }
}
