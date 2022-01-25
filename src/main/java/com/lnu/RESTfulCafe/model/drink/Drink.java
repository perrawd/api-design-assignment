package com.lnu.RESTfulCafe.model.drink;

import java.util.ArrayList;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Drink {
    private @Id @GeneratedValue Long id;
    private String name;
    private ArrayList<String> ingredients;
    private double price;

    public Drink () {}

    public Drink(Long id, String name, ArrayList<String> ingredients, double price) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.price = price;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drink drink = (Drink) o;
        return Double.compare(drink.price, price) == 0
                && Objects.equals(id, drink.id)
                && Objects.equals(name, drink.name)
                && Objects.equals(ingredients, drink.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, ingredients, price);
    }
}
