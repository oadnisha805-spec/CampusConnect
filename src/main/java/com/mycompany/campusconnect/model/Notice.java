package com.mycompany.campusconnect.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;


@Entity
public class Notice {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String title;

  private String message;

    private String date;

    @CreationTimestamp
    private LocalDateTime createdAt;



    public Notice() {

    }



    public Notice(String title, String description, String date) {

        this.title = title;
        this.message = description;
        this.date = date;

    }



    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }



    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }



  public String getMessage() {
    return message;
}


public void setMessage(String message) {
    this.message = message;
}


    public String getDate() {
        return date;
    }


    public void setDate(String date) {
        this.date = date;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}