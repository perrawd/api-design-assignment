package com.lnu.RESTfulCafe.model.drink;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.lnu.RESTfulCafe.controller.DrinkController;

import com.lnu.RESTfulCafe.model.drink.Drink;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class DrinkModelAssembler implements RepresentationModelAssembler<Drink, EntityModel<Drink>> {

    @Override
    public EntityModel<Drink> toModel(Drink drink) {
        return EntityModel.of(drink,
                linkTo(methodOn(DrinkController.class).one(drink.getId().toString())).withSelfRel(),
                linkTo(methodOn(DrinkController.class).all()).withRel("drinks"));
    }
}
