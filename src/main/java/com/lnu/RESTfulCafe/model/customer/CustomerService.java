package com.lnu.RESTfulCafe.model.customer;

import java.util.List;

public interface CustomerService {
    Customer saveUser(Customer user);
    Customer getUser(String username);
    List<Customer> getUsers();
}
