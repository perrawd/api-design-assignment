package com.lnu.RESTfulCafe.model.employee;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Collection<Role> roles = new ArrayList<>();

    public Employee () {}

    public Employee(Long id, String firstName, String lastName, String username, String password, Collection<Role> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName) && Objects.equals(username, employee.username) && Objects.equals(password, employee.password) && Objects.equals(roles, employee.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, username, password, roles);
    }
}
