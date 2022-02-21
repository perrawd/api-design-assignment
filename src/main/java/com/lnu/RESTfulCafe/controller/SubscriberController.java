package com.lnu.RESTfulCafe.controller;

import com.lnu.RESTfulCafe.controller.error.SubscriberNotFoundException;
import com.lnu.RESTfulCafe.model.subscriber.Subscriber;
import com.lnu.RESTfulCafe.model.subscriber.SubscriberModelAssembler;
import com.lnu.RESTfulCafe.model.subscriber.SubscriberRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class SubscriberController {

    private final SubscriberRepository repository;
    private final SubscriberModelAssembler assembler;
    
    public SubscriberController(SubscriberRepository repository, SubscriberModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/subscribers")
    public CollectionModel<EntityModel<Subscriber>> all() {

        List<EntityModel<Subscriber>> subscribers =
                repository.findAll()
                        .stream()
                        .map(assembler::toModel)
                        .collect(Collectors.toList());

        return CollectionModel.of(subscribers, linkTo(methodOn(SubscriberController.class).all()).withSelfRel());
    }

    @PostMapping("/subscribers")
    ResponseEntity<?> newSubscriber(@RequestBody Subscriber newSubscriber) {

        EntityModel<Subscriber> entityModel = assembler.toModel(repository.save(newSubscriber));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @GetMapping("/subscribers/{id}")
    public EntityModel<Subscriber> one(@PathVariable Long id) {

        Subscriber subscriber = repository.findById(id)
                .orElseThrow(() -> new SubscriberNotFoundException(id));

        return assembler.toModel(subscriber);
    }

    @PutMapping("/subscribers/{id}")
    ResponseEntity<?> replaceSubscriber(@RequestBody Subscriber newSubscriber, @PathVariable Long id) {

        Subscriber updatedSubscriber = repository.findById(id) //
                .map(subscriber -> {
                    subscriber.setUrl(newSubscriber.getUrl());
                    subscriber.setEvent(newSubscriber.getEvent());
                    return repository.save(subscriber);
                }) //
                .orElseGet(() -> {
                    newSubscriber.setId(id);
                    return repository.save(newSubscriber);
                });

        EntityModel<Subscriber> entityModel = assembler.toModel(updatedSubscriber);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/subscribers/{id}")
    ResponseEntity<?> deleteSubscriber(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
