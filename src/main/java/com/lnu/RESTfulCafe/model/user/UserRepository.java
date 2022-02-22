package com.lnu.RESTfulCafe.model.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    @Query(value="SELECT * FROM user JOIN user_roles ON user.id = user_roles.user_id WHERE user_roles.roles_id = 4;", nativeQuery = true)
    List<User> getAllCustomers();
}
