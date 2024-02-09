package com.SBProject.hellospring.rest;

import com.SBProject.hellospring.dao.EmployeeDAO;
import com.SBProject.hellospring.entity.Employee;
import com.SBProject.hellospring.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

        private EmployeeService employeeService;


        public EmployeeRestController(EmployeeService theEmployeeService){
            employeeService = theEmployeeService;
        }

        @GetMapping("/employees")
        public List<Employee> findAll(){
            return employeeService.findAll();
        }
}
