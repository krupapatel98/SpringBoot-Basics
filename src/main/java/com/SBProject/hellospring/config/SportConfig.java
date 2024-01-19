package com.SBProject.hellospring.config;

import com.SBProject.hellospring.common.Coach;
import com.SBProject.hellospring.common.SwimCoach;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SportConfig {

    @Bean
    public Coach swimCoach(){           // bean id defaults to the method name with first letter lowercase
        return new SwimCoach();
    }
}
