package com.lnu.RESTfulCafe.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.lnu.RESTfulCafe.controller.error.BeanNotFoundException;
import com.lnu.RESTfulCafe.controller.error.ResourceIDNotFoundException;
import com.lnu.RESTfulCafe.event.drink.DrinkAddedEventPublisher;
import com.lnu.RESTfulCafe.model.drink.Drink;
import com.lnu.RESTfulCafe.model.drink.DrinkModelAssembler;
import com.lnu.RESTfulCafe.model.drink.DrinkRepository;
import com.lnu.RESTfulCafe.controller.error.DrinkNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class DrinkController {

    private final DrinkRepository repository;
    private final DrinkModelAssembler assembler;

    @Autowired
    private DrinkAddedEventPublisher publisher;

    DrinkController(DrinkRepository repository, DrinkModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/drinks")
    public CollectionModel<EntityModel<Drink>> all() {

        List<EntityModel<Drink>> drinks =
                repository.findAll()
                        .stream()
                        .map(assembler::toModel)
                        .collect(Collectors.toList());

        return CollectionModel.of(drinks, linkTo(methodOn(DrinkController.class).all()).withSelfRel());
    }

    @PostMapping("/drinks")
    ResponseEntity<?> newDrink(@RequestBody Drink newDrink) {

        EntityModel<Drink> entityModel = assembler.toModel(repository.save(newDrink));

        // Publish event with Drink.
        publisher.publishEvent(entityModel);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @GetMapping("/drinks/{variable}")
    public EntityModel<Drink> one(@PathVariable String variable) {

        Drink drink = this.getDrinkByPathVariable(variable);

        return assembler.toModel(drink);
    }

    @PutMapping("/drinks/{variable}")
    ResponseEntity<?> replaceDrink(@RequestBody Drink newDrink, @PathVariable String variable) {

        Long id = this.getDrinkByPathVariable(variable).getId();

        Drink updatedDrink = repository.findById(id) //
                .map(drink -> {
                    drink.setName(newDrink.getName());
                    drink.setBeverage(newDrink.getBeverage());
                    drink.setIngredients(newDrink.getIngredients());
                    drink.setPrice(newDrink.getPrice());
                    drink.setType(newDrink.getType());
                    return repository.save(drink);
                }) //
                .orElseGet(() -> {
                    newDrink.setId(id);
                    return repository.save(newDrink);
                });

        EntityModel<Drink> entityModel = assembler.toModel(updatedDrink);

        return ResponseEntity //
                //.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .ok()
                .body(entityModel);
    }

    @DeleteMapping("/drinks/{variable}")
    ResponseEntity<?> deleteDrink(@PathVariable String variable) {

        Long id = this.getDrinkByPathVariable(variable).getId();
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    // Get resource by path variable (resource name or ID)
    Drink getDrinkByPathVariable(String pathVariable) {
        try {
            long pathLong = Long.parseLong(pathVariable);
            return repository.findById(pathLong)
                    .orElseThrow(() -> new ResourceIDNotFoundException(pathLong));
        } catch (final NumberFormatException e) {
            return repository.findByName(pathVariable)
                    .orElseThrow(() -> new BeanNotFoundException(pathVariable));
        }
    }
}
