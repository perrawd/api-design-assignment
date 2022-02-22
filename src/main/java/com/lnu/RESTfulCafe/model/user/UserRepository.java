package com.lnu.RESTfulCafe.model.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    @Query(value="SELECT * FROM user JOIN user_roles ON user.id = user_roles.user_id WHERE user_roles.roles_id = 4;", nativeQuery = true)
    List<User> getAllCustomers();
    @Query(value="SELECT * FROM bean_demo.user JOIN bean_demo.user_roles ON bean_demo.user.id = bean_demo.user_roles.user_id WHERE bean_demo.user_roles.roles_id = 1 OR bean_demo.user_roles.roles_id = 2 OR bean_demo.user_roles.roles_id = 3;", nativeQuery = true)
    List<User> getAllEmployees();
}
