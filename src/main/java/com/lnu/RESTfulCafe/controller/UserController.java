package com.lnu.RESTfulCafe.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.lnu.RESTfulCafe.model.user.*;
import com.lnu.RESTfulCafe.controller.error.UserNotFoundException;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {

    private final UserRepository repository;
    private final UserModelAssembler assembler;
    private final UserService userService;

    UserController(UserRepository repository, UserModelAssembler assembler, UserService userService) {
        this.repository = repository;
        this.assembler = assembler;
        this.userService = userService;
    }

    @GetMapping("/employees")
    public CollectionModel<EntityModel<User>> all() {

        List<EntityModel<User>> employees =
                repository.findAll()
                        .stream()
                        .map(assembler::toModel)
                        .collect(Collectors.toList());

        return CollectionModel.of(employees, linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    @PostMapping("/employees")
    ResponseEntity<?> newEmployee(@RequestBody User newUser) {

        EntityModel<User> entityModel = assembler.toModel(userService.saveUser(newUser));
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/employees").toUriString());
        return ResponseEntity //
                .created(uri)
                .body("User created");
    }

    @GetMapping("/employees/{id}")
    public EntityModel<User> one(@PathVariable Long id) {

        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return assembler.toModel(user);
    }

    @PutMapping("/employees/{id}")
    ResponseEntity<?> replaceEmployee(@RequestBody User newUser, @PathVariable Long id) {

        User updatedUser = repository.findById(id) //
                .map(employee -> {
                    employee.setName(newUser.getName());
                    employee.setFirstName(newUser.getFirstName());
                    employee.setLastName(newUser.getLastName());
                    employee.setRoles((ArrayList<Role>) newUser.getRoles());
                    return repository.save(employee);
                }) //
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });

        EntityModel<User> entityModel = assembler.toModel(updatedUser);

        return ResponseEntity //
                //.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .ok()
                .body("User updated.");
    }

    @DeleteMapping("/employees/{id}")
    ResponseEntity<?> deleteEmployee(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
