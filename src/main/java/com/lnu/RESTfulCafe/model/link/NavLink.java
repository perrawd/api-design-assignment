package com.lnu.RESTfulCafe.model.link;

import org.springframework.hateoas.RepresentationModel;

public class NavLink extends RepresentationModel<NavLink> {
    public String name, description;
}
