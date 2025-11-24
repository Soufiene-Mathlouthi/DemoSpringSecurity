package com.tw.springboot.demosecurity.demosecurity.service;

import com.tw.springboot.demosecurity.demosecurity.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAll();

    Employee findById(int theId);

    Employee save(Employee theEmployee);

    void deleteById(int theId);

}
