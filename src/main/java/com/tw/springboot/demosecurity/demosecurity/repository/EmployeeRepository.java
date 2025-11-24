package com.tw.springboot.demosecurity.demosecurity.repository;

import com.tw.springboot.demosecurity.demosecurity.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    public List<Employee> findAllByOrderByLastNameAsc();
}
