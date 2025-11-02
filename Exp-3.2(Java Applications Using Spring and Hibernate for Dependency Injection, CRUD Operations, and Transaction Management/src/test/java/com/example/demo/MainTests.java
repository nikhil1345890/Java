package com.example.demo;

import com.example.AppConfig;
import com.example.controller.SampleController;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MainTests {

    @Test
    public void testSampleControllerBean() {
        // Initialize Spring context using Java-based configuration
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Retrieve the SampleController bean
        SampleController sampleController = context.getBean(SampleController.class);

        // Assert that the bean is not null
        assertNotNull(sampleController);

        // Close context
        context.close();
    }
}
