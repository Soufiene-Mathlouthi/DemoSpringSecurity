package com.tw.springboot.demosecurity.demosecurity.controller;

import com.tw.springboot.demosecurity.demosecurity.entity.Employee;
import com.tw.springboot.demosecurity.demosecurity.service.EmployeeService;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DemoController {
    private EmployeeService employeeService;

    private ObjectMapper objectMapper;

    public DemoController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees/{id}")
    public Employee findById(@PathVariable int id) {
        Employee employee = employeeService.findById(id);

        if(employee == null)
            throw new RuntimeException("Employee with id " + id + " not found");

        return employee;
    }

    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee) {
        employee.setId(0);
        Employee dbEmployee = employeeService.save(employee);
        return dbEmployee;
    }

    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee theEmployee) {

        Employee dbEmployee = employeeService.save(theEmployee);

        return dbEmployee;
    }

    @GetMapping("/employees")
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    @DeleteMapping("/employees/{id}")
    public String deleteEmployee(@PathVariable int id) {

        Employee tempEmployee = employeeService.findById(id);

        if(tempEmployee == null)
            throw new RuntimeException("Employee with id " + id + " not found");

        employeeService.deleteById(id);
        return "Employee  with id " + id + " deleted";
    }

}
