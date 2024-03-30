package com.SBProject.hellospring.rest;

import com.SBProject.hellospring.dao.EmployeeDAO;
import com.SBProject.hellospring.entity.Employee;
import com.SBProject.hellospring.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

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

        //add mapping for GET /employees/{employeeId}
        @GetMapping("/employees/{employeeId}")
        public Employee getEmployee(@PathVariable int employeeId){
            Employee theEmployee = employeeService.findById(employeeId);

            if(theEmployee == null){
                throw new RuntimeException("Employee id not found - " + employeeId);
            }

            return theEmployee;
        }

    //add mapping for POST /employees - add new employee
    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee theEmployee){

            theEmployee.setId(0);
            Employee dbEmployee = employeeService.save(theEmployee);
            return dbEmployee;
    }

    //add mapping for PUT /employees - update existing employee
    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee theEmployee){
            Employee dbEmployee = employeeService.save(theEmployee);

            return dbEmployee;
    }

    //add mapping for DELETE /employees/{employeeId} - delete existing employee
    @DeleteMapping("/employees/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId) {
        Employee tempEmployee = employeeService.findById(employeeId);

        // throw exception if null
        if (tempEmployee == null){
            throw new RuntimeException("Employee id not found - " + employeeId);
        }
        employeeService.deleteById(employeeId);

        return "Deleted employee id - " + employeeId;
    }

}
