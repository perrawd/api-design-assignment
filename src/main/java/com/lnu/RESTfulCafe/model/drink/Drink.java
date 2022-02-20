package com.lnu.RESTfulCafe.model.drink;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lnu.RESTfulCafe.model.order.Order;

import java.util.ArrayList;
import java.util.Objects;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@SequenceGenerator(name = "drinkseq", initialValue = 1)
public class Drink {
    private @Id @GeneratedValue(generator = "drinkseq") @Column(name = "id") Long id;
    private @Column(unique=true) String name;
    private Type type;
    private Beverage beverage;
    private ArrayList<String> ingredients;
    private double price;
    @JsonIgnore
    @OneToOne(mappedBy = "drink")
    //@JoinColumn(name = "order_id")
    private Order order;

    public Drink () {}

    public Drink(Long id, String name, Type type, Beverage beverage, ArrayList<String> ingredients, double price, Order order) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.beverage = beverage;
        this.ingredients = ingredients;
        this.price = price;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Beverage getBeverage() {
        return beverage;
    }

    public void setBeverage(Beverage beverage) {
        this.beverage = beverage;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drink drink = (Drink) o;
        return Double.compare(drink.price, price) == 0
                && Objects.equals(id, drink.id)
                && Objects.equals(name, drink.name)
                && Objects.equals(type, drink.type)
                && Objects.equals(beverage, drink.beverage)
                && Objects.equals(ingredients, drink.ingredients)
                && Objects.equals(order, drink.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, beverage, ingredients, price, order);
    }
}
