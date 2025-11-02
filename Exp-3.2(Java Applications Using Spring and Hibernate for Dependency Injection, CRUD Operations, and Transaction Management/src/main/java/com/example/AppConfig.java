package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.controller.SampleController;

@Configuration
public class AppConfig {

    // Define the Course bean
    @Bean
    public Course course() {
        return new Course("Java Programming");
    }

    // Define the SampleController bean and inject the Course bean
    @Bean
    public SampleController sampleController() {
        return new SampleController(course());  // Inject the Course bean into SampleController
    }
}
