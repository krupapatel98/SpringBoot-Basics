package com.SBProject.hellospring.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class FunRestController {
    @GetMapping("/hello")
    public String sayHello(){
        return "Hello Spring!!";
    }

    //injecting the properties from application file --
    @Value("${coach.name}")
    private String coachName;

    @Value("${team.name}")
    private String teamName;

    //using the injected properties variables --
    @GetMapping("/teaminfo")
    public String getTeamInfo(){
        return "Coach: "+ coachName + ", Team Name: " + teamName;
    }
}
