package com.SBProject.hellospring.rest;

import com.SBProject.hellospring.common.Coach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    //define private field for dependency

    private Coach myCoach;

    //defining constructor for dependency injection
    @Autowired
    public DemoController(Coach theCoach){
        myCoach = theCoach;
    }

//    //Example for setter injection
//    @Autowired
//    public void setCoach(Coach theCoach){           //the setCoach method can have other name as well as far as it is Autowired
//        myCoach=theCoach;
//    }

    @GetMapping("/dailyworkout")
    public String getDailyWorkout(){
        return myCoach.getDailyWorkout();
    }
}
