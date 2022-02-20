package com.lnu.RESTfulCafe.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.lnu.RESTfulCafe.controller.error.ResourceIDNotFoundException;
import com.lnu.RESTfulCafe.event.bean.BeanAddedEventPublisher;
import com.lnu.RESTfulCafe.model.bean.Bean;
import com.lnu.RESTfulCafe.model.bean.BeanModelAssembler;
import com.lnu.RESTfulCafe.model.bean.BeanRepository;
import com.lnu.RESTfulCafe.controller.error.BeanNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // GET. Collection of all Bean resources.
    @GetMapping("/beans")
    public CollectionModel<EntityModel<Bean>> all() {

        List<EntityModel<Bean>> beans =
                repository.findAll()
                        .stream()
                        .map(assembler::toModel)
                        .collect(Collectors.toList());

        return CollectionModel.of(beans, linkTo(methodOn(BeanController.class).all()).withSelfRel());
    }

    // POST. Post of new resource.
    @PostMapping("/beans")
    ResponseEntity<?> newBean(@RequestBody Bean newBean) {

        EntityModel<Bean> entityModel = assembler.toModel(repository.save(newBean));

        // Publish event with Bean.
        publisher.publishEvent(newBean);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    // GET. Get single resource with Name path variable.
    @GetMapping("/beans/{name}")
    public EntityModel<Bean> oneByName(@PathVariable String name) {

        Bean bean = repository.findByName(name)
                .orElseThrow(() -> new BeanNotFoundException(name));

        return assembler.toModel(bean);
    }

    // GET. Get single resource with ID request param.
    @GetMapping(value = "/beans", params = {"id"})
    public EntityModel<Bean> one(@RequestParam Long id) {

        Bean bean = repository.findById(id)
                .orElseThrow(() -> new ResourceIDNotFoundException(id));

        return assembler.toModel(bean);
    }

    // PUT. Put update of single resource.
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
