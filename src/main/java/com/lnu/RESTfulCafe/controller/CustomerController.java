package com.lnu.RESTfulCafe.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.lnu.RESTfulCafe.controller.error.CustomerNotFoundException;
import com.lnu.RESTfulCafe.model.customer.Customer;
import com.lnu.RESTfulCafe.model.customer.CustomerModelAssembler;
import com.lnu.RESTfulCafe.model.customer.CustomerRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CustomerController {

    private final CustomerRepository repository;
    private final CustomerModelAssembler assembler;
    
    CustomerController(CustomerRepository repository, CustomerModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/customers")
    public CollectionModel<EntityModel<Customer>> all() {

        List<EntityModel<Customer>> customers =
                repository.findAll()
                        .stream()
                        .map(assembler::toModel)
                        .collect(Collectors.toList());

        return CollectionModel.of(customers, linkTo(methodOn(CustomerController.class).all()).withSelfRel());
    }

    @PostMapping("/customers")
    ResponseEntity<?> newCustomer(@RequestBody Customer newCustomer) {

        EntityModel<Customer> entityModel = assembler.toModel(repository.save(newCustomer));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @GetMapping("/customers/{id}")
    public EntityModel<Customer> one(@PathVariable Long id) {

        Customer customer = repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        return assembler.toModel(customer);
    }

    @PutMapping("/customers/{id}")
    ResponseEntity<?> replaceCustomer(@RequestBody Customer newCustomer, @PathVariable Long id) {

        Customer updatedCustomer = repository.findById(id) //
                .map(customer -> {
                    customer.setUsername(newCustomer.getUsername());
                    customer.setFirstName(newCustomer.getFirstName());
                    customer.setLastName(newCustomer.getLastName());
                    return repository.save(customer);
                }) //
                .orElseGet(() -> {
                    newCustomer.setId(id);
                    return repository.save(newCustomer);
                });

        EntityModel<Customer> entityModel = assembler.toModel(updatedCustomer);

        return ResponseEntity //
                .ok()
                .body(entityModel);
    }

    @DeleteMapping("/customers/{id}")
    ResponseEntity<?> deleteCustomer(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
