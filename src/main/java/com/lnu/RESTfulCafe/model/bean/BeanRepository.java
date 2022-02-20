package com.lnu.RESTfulCafe.model.bean;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BeanRepository extends JpaRepository<Bean, Long> {
    Optional<Bean> findByName(String name);
}
