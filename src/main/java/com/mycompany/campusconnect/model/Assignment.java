package com.mycompany.campusconnect.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Integer, not int - a form field arriving empty converts to null,
    // and a primitive can't hold that (same bug we hit with Fee earlier).
    private Integer courseId;

    private String title;

    private String description;

    private String dueDate;


    public Assignment() {
    }

    public Assignment(Integer courseId, String title, String description, String dueDate) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}