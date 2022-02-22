package com.lnu.RESTfulCafe.model.user;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@SequenceGenerator(name = "userseq", initialValue = 1)
public class User {
    private @Id @GeneratedValue(generator = "userseq") Long id;
    private String firstname;
    private String lastname;
    private @Column(unique=true) String username;
    private String password;
    @ManyToMany(fetch= FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    public User() {}

    public User(Long id, String firstname, String lastname, String username, String password, ArrayList<Role> roles) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
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

    public String getName() {
        return this.firstname + " " + this.lastname;
    }

    public void setName(String name) {
        String[] parts = name.split(" ");
        this.firstname = parts[0];
        this.lastname = parts[1];
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastname) {
        this.lastname = lastname;
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

    public void setRoles(ArrayList<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(firstname, user.firstname) && Objects.equals(lastname, user.lastname) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, username, password, roles);
    }
}
