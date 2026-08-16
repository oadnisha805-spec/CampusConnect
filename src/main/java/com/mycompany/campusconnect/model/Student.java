package com.mycompany.campusconnect.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;


@Entity
public class Student {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


  private String name;
private String email;
private String department;
private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;


    // Empty Constructor
    public Student() {

    }



    // Constructor
    public Student(String name, String email, String password, String department) {

        this.name = name;
        this.email = email;
        this.password = password;
        this.department = department;

    }



    // Getters and Setters

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }



    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }



    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }



    public String getDepartment() {
        return department;
    }


    public void setDepartment(String department) {
        this.department = department;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}