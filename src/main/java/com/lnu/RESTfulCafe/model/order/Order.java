package com.lnu.RESTfulCafe.model.order;

import com.lnu.RESTfulCafe.model.drink.Drink;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "CUSTOMER_ORDER")
@SequenceGenerator(name = "orderseq", initialValue = 1, allocationSize=1)
public class Order {
    private @Id @GeneratedValue(generator = "orderseq") @Column(name = "id") Long id;
    private Status status;
    private String special;
    // Add drink/bean
    // TODO: CHANGE TO MANYTOMANY
    @OneToOne(cascade = CascadeType.MERGE) // ALL
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Drink drink;

    Order() {}

    public Order(Status status, String special, Drink drink) {
        this.status = status;
        this.special = special;
        this.drink = drink;
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

    public Drink getDrink() {
        return drink;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && status == order.status && Objects.equals(special, order.special) && Objects.equals(drink, order.drink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, special, drink);
    }
}
