package com.example.controller;

import com.example.Course;

public class SampleController {

    private Course course;

    // Constructor-based dependency injection
    public SampleController(Course course) {
        this.course = course;
    }

    public void displayCourse() {
        System.out.println("The course is: " + course.getCourseName());
    }
}
