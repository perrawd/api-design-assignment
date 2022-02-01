package com.lnu.RESTfulCafe.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.lnu.RESTfulCafe.event.bean.BeanAddedEventPublisher;
import com.lnu.RESTfulCafe.model.bean.Bean;
import com.lnu.RESTfulCafe.model.bean.BeanModelAssembler;
import com.lnu.RESTfulCafe.model.bean.BeanRepository;
import com.lnu.RESTfulCafe.controller.error.BeanNotFoundException;

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
public class BeanController {

    private final BeanRepository repository;
    private final BeanModelAssembler assembler;

    @Autowired
    private BeanAddedEventPublisher publisher;

    BeanController(BeanRepository repository, BeanModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/beans")
    public CollectionModel<EntityModel<Bean>> all() {

        List<EntityModel<Bean>> beans =
                repository.findAll()
                        .stream()
                        .map(assembler::toModel)
                        .collect(Collectors.toList());

        return CollectionModel.of(beans, linkTo(methodOn(BeanController.class).all()).withSelfRel());
    }

    @PostMapping("/beans")
    ResponseEntity<?> newBean(@RequestBody Bean newBean) {

        EntityModel<Bean> entityModel = assembler.toModel(repository.save(newBean));

        // Publish event with Bean.
        publisher.publishEvent(newBean);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @GetMapping("/beans/{id}")
    public EntityModel<Bean> one(@PathVariable Long id) {

        Bean bean = repository.findById(id)
                .orElseThrow(() -> new BeanNotFoundException(id));

        return assembler.toModel(bean);
    }

    @PutMapping("/beans/{id}")
    ResponseEntity<?> replaceBean(@RequestBody Bean newBean, @PathVariable Long id) {

        Bean updatedBean = repository.findById(id) //
                .map(bean -> {
                    bean.setName(newBean.getName());
                    //bean.setRole(newBean.getRole());
                    return repository.save(bean);
                }) //
                .orElseGet(() -> {
                    newBean.setId(id);
                    return repository.save(newBean);
                });

        EntityModel<Bean> entityModel = assembler.toModel(updatedBean);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/beans/{id}")
    ResponseEntity<?> deleteBean(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
