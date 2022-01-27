package com.lnu.RESTfulCafe.model.employee;

import java.util.List;

public interface EmployeeService {
    Employee saveUser(Employee user);
    Role saveRole(Role role);
    void addRoleToUser(String userName, String roleName);
    Employee getUser(String userName);
    List<Employee> getUsers();
}
