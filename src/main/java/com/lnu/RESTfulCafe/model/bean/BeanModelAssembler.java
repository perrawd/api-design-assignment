package com.lnu.RESTfulCafe.model.bean;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.lnu.RESTfulCafe.controller.BeanController;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class BeanModelAssembler implements RepresentationModelAssembler<Bean, EntityModel<Bean>> {

    @Override
    public EntityModel<Bean> toModel(Bean bean) {
        return EntityModel.of(bean,
                linkTo(methodOn(BeanController.class).one(bean.getId())).withSelfRel(),
                linkTo(methodOn(BeanController.class).all()).withRel("beans"));
    }
}
