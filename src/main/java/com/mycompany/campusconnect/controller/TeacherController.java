package com.mycompany.campusconnect.controller;

import com.mycompany.campusconnect.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class TeacherController {


    @Autowired
    private TeacherRepository teacherRepository;


    @GetMapping("/teachers")
    public String teachers(Model model){

        model.addAttribute("teachers", teacherRepository.findAll());

        return "teachers";
    }

}