package com.lnu.RESTfulCafe.controller;

import com.lnu.RESTfulCafe.model.link.NavLink;

import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class RootController {
    @GetMapping("/")
    public CollectionModel<NavLink> root() {
        ArrayList<NavLink> links = new ArrayList<>();

        NavLink beansLink = new NavLink();
        beansLink.name = "beans";
        beansLink.description = "The best and high quality coffee beans from around the world.";
        beansLink.add(linkTo(methodOn(BeanController.class).all()).withRel("beans"));
        links.add(beansLink);

        NavLink drinksLink = new NavLink();
        drinksLink.name = "Drinks";
        drinksLink.description = "The best and high quality coffee drinks made with ❤️";
        drinksLink.add(linkTo(methodOn(DrinkController.class).all()).withRel("drinks"));
        links.add(drinksLink);

        return CollectionModel.of(links);
    }
}
