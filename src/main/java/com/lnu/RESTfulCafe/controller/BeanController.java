package com.lnu.RESTfulCafe.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.lnu.RESTfulCafe.controller.error.ResourceIDNotFoundException;
import com.lnu.RESTfulCafe.event.bean.BeanAddedEventPublisher;
import com.lnu.RESTfulCafe.model.bean.Bean;
import com.lnu.RESTfulCafe.model.bean.BeanModelAssembler;
import com.lnu.RESTfulCafe.model.bean.BeanRepository;
import com.lnu.RESTfulCafe.controller.error.BeanNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;

import javax.swing.text.html.Option;

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

        if (repository.findByName(newBean.getName()).isPresent()) {
            return ResponseEntity //
                    .status(HttpStatus.CONFLICT)
                    .body("A resource with this name already exists.");
        }

        EntityModel<Bean> entityModel = assembler.toModel(repository.save(newBean));

        // Publish event with Bean.
        publisher.publishEvent(entityModel);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    // GET. Get single resource with Name path variable.
    @GetMapping("/beans/{variable}")
    public EntityModel<Bean> oneBean(@PathVariable String variable) {

        Bean bean = this.getBeanByPathVariable(variable);

        return assembler.toModel(bean);
    }

    // PUT. Put update of single resource.
    @PutMapping("/beans/{variable}")
    ResponseEntity<?> replaceBean(@RequestBody Bean newBean, @PathVariable String variable) {

        Long id = this.getBeanByPathVariable(variable).getId();

        Bean updatedBean = repository.findById(id) //
                .map(bean -> {
                    bean.setName(newBean.getName());
                    bean.setAroma(newBean.getAroma());
                    bean.setOrigin(newBean.getOrigin());
                    bean.setRegion(newBean.getRegion());
                    bean.setReviewed(newBean.getReviewed());
                    bean.setVariety(newBean.getVariety());
                    return repository.save(bean);
                }) //
                .orElseGet(() -> {
                    newBean.setId(id);
                    return repository.save(newBean);
                });

        EntityModel<Bean> entityModel = assembler.toModel(updatedBean);

        return ResponseEntity //
                //.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .ok()
                .body(entityModel);
    }

    @DeleteMapping("/beans/{variable}")
    ResponseEntity<?> deleteBean(@PathVariable String variable) {

        Long id = this.getBeanByPathVariable(variable).getId();
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    // Get resource by path variable (resource name or ID)
    Bean getBeanByPathVariable(String pathVariable) {
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
