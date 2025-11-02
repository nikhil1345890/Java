package com.example;

import com.example.dao.StudentDAO;

public class MainHibernate {
    public static void main(String[] args) {
        StudentDAO dao = new StudentDAO();

        // CREATE
        Student s1 = new Student("Alice", "alice@example.com", 22);
        dao.saveStudent(s1);

        // READ
        System.out.println("Fetching all students:");
        dao.getAllStudents().forEach(System.out::println);

        // UPDATE
        s1.setAge(23);
        dao.updateStudent(s1);

        // READ single
        Student fetched = dao.getStudentById(s1.getId());
        System.out.println("Fetched by ID: " + fetched);

        // DELETE
        dao.deleteStudent(s1.getId());
    }
}
