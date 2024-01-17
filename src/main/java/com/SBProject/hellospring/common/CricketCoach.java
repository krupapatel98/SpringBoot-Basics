package com.SBProject.hellospring.common;

import org.springframework.stereotype.Component;

//@Component marks as a spring bean. If this wont be marked
@Component
public class CricketCoach implements Coach{

    @Override
    public String getDailyWorkout(){
        return "Practice fast bowling for 15 minutes :) :)";
    }
}
