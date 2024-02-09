package com.SBProject.hellospring.dao;

import com.SBProject.hellospring.entity.Employee;

import java.util.List;

public interface EmployeeDAO {
    List<Employee> findAll();
}
