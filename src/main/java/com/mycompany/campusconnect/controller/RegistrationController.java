package com.mycompany.campusconnect.controller;


import com.mycompany.campusconnect.model.Student;
import com.mycompany.campusconnect.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
public class RegistrationController {


    @Autowired
    private StudentRepository studentRepository;



    @GetMapping("/register")
    public String registerPage(){

        return "register";

    }



    @PostMapping("/register")
    public String registerStudent(Student student, Model model){


        studentRepository.save(student);


        model.addAttribute(
                "message",
                "Registration Successful!"
        );


        return "register-success";

    }

}