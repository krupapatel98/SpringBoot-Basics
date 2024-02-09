package com.SBProject.hellospring.service;

import com.SBProject.hellospring.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();
}
