package com.mycompany.campusconnect.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;


@Entity
public class Course {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String courseName;

    private String courseCode;

    private String instructor;

    @CreationTimestamp
    private LocalDateTime createdAt;



    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getCourseName() {
        return courseName;
    }


    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }


    public String getCourseCode() {
        return courseCode;
    }


    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }


    public String getInstructor() {
        return instructor;
    }


    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}