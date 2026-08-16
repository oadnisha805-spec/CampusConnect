package com.mycompany.campusconnect.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Fee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Integer/Double instead of int/double: if a form field ever arrives
    // empty, Spring's binder converts "" to null before assignment. A
    // primitive can't hold null and throws IllegalArgumentException
    // ("A null value cannot be assigned to a primitive type"). Wrapper
    // types absorb that safely instead of crashing the whole request.
    private Integer studentId;

    private String semester;

    private Double amount;

    private String dueDate;

    // "Paid" or "Unpaid"
    private String status;


    public Fee() {
    }

    public Fee(Integer studentId, String semester, Double amount, String dueDate, String status) {
        this.studentId = studentId;
        this.semester = semester;
        this.amount = amount;
        this.dueDate = dueDate;
        this.status = status;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}