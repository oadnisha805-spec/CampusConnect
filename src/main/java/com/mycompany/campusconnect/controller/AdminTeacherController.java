package com.mycompany.campusconnect.controller;


import com.mycompany.campusconnect.model.Teacher;
import com.mycompany.campusconnect.repository.TeacherRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class AdminTeacherController {


    @Autowired
    private TeacherRepository teacherRepository;



    @GetMapping("/admin/add-teacher")
    public String addTeacherPage(){

        return "add-teacher";

    }



    @PostMapping("/admin/add-teacher")
    public String saveTeacher(Teacher teacher, Model model){


        teacherRepository.save(teacher);


        model.addAttribute(
                "message",
                "Teacher Added Successfully!"
        );


        return "teacher-success";

    }

}