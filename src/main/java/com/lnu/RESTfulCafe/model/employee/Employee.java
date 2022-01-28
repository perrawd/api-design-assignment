package com.lnu.RESTfulCafe.model.employee;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static javax.persistence.GenerationType.AUTO;

@Entity
public class Employee {
    private @Id @GeneratedValue(strategy = AUTO) Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    @ManyToMany(fetch= FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    public Employee () {}

    public Employee(Long id, String firstName, String lastName, String userName, String password, ArrayList<Role> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.firstName + " " + this.lastName;
    }

    public void setName(String name) {
        String[] parts = name.split(" ");
        this.firstName = parts[0];
        this.lastName = parts[1];
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public void setRoles(ArrayList<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName) && Objects.equals(userName, employee.userName) && Objects.equals(password, employee.password) && Objects.equals(roles, employee.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, userName, password, roles);
    }
}
