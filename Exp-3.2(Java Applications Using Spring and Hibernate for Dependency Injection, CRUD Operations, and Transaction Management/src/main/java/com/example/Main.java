package com.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.example.controller.SampleController;

public class Main {
    public static void main(String[] args) {
        // Initialize Spring context using Java-based configuration
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Retrieve SampleController bean from the Spring context
        SampleController sampleController = context.getBean(SampleController.class);

        // Call a method on the controller (optional for demonstration)
        sampleController.displayCourse();

        // Close the Spring context
        context.close();
    }
}
