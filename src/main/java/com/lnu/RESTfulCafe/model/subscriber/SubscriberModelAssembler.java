package com.lnu.RESTfulCafe.model.subscriber;

import com.lnu.RESTfulCafe.controller.SubscriberController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SubscriberModelAssembler implements RepresentationModelAssembler<Subscriber, EntityModel<Subscriber>> {

    @Override
    public EntityModel<Subscriber> toModel(Subscriber subscriber) {
        return EntityModel.of(subscriber,
                linkTo(methodOn(SubscriberController.class).one(subscriber.getId())).withSelfRel());
    }
}
