package com.SBProject.hellospring.rest;

import com.SBProject.hellospring.entity.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.origin.SystemEnvironmentOrigin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {

    private List<Student> theStudents;

    //define @PostConstruct to load the student data.. only called once!
    @PostConstruct
    public void loadData() {
        theStudents = new ArrayList<>();
        theStudents.add(new Student("Yash", "Patel", "yash@gmail.com"));
        theStudents.add(new Student("Vishwa", "Patel", "vishwa@gmail.com"));
        theStudents.add(new Student("Jainil", "Patel", "jainil@gmail.com"));
    }


    // define endpoints for "/students" - return a list of students
    @GetMapping("/students")
    public List<Student> getStudents(){

        return theStudents;
    }

    //define endpoint or "/students/{studentId}" - return student at index
    @GetMapping("/students/{studentId}")
    public Student getStudent(@PathVariable int studentId){

        if((studentId>= theStudents.size()) || (studentId < 0)){
            throw new StudentNotFoundException("Student id is not found -  " + studentId);
        }
        return theStudents.get(studentId);
    }

}
