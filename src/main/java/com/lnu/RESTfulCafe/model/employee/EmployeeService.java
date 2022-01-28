package com.lnu.RESTfulCafe.model.employee;

import java.util.List;

public interface EmployeeService {
    Employee saveUser(Employee user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    Employee getUser(String username);
    List<Employee> getUsers();
}
