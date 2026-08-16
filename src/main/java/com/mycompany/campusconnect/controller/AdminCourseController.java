package com.mycompany.campusconnect.controller;


import com.mycompany.campusconnect.model.Course;
import com.mycompany.campusconnect.repository.CourseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class AdminCourseController {


    @Autowired
    private CourseRepository courseRepository;



    @GetMapping("/admin/add-course")
    public String addCoursePage(){

        return "add-course";

    }



    @PostMapping("/admin/add-course")
    public String saveCourse(Course course, Model model){


        courseRepository.save(course);


        model.addAttribute(
                "message",
                "Course Added Successfully!"
        );


        return "course-success";

    }

}