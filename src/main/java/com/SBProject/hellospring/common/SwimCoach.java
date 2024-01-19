package com.SBProject.hellospring.common;


//We are not using @Component as we need to configure it using custom approach.
public class SwimCoach implements Coach{

    public SwimCoach(){
        System.out.println("In constructor: " + getClass().getSimpleName());
    }

    @Override
    public String getDailyWorkout() {
        return "Swim 1000 meters as warmup!";
    }
}
