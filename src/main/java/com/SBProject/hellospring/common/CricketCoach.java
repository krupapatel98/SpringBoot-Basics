package com.SBProject.hellospring.common;

import org.springframework.stereotype.Component;

//@Component marks as a spring bean. If this wont be marked
@Component
//added @Primary annotation to initialize this implementation by default.
//@Primary
public class CricketCoach implements Coach{

    public CricketCoach(){
        System.out.println("In constructor: "+ getClass().getSimpleName());
    }
    @Override
    public String getDailyWorkout(){
        return "Practice fast bowling for 15 minutes :) :)";
    }
}
