package com.mycompany.campusconnect.controller;


import com.mycompany.campusconnect.model.Course;
import com.mycompany.campusconnect.repository.CourseRepository;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
public class CourseController {



    @Autowired
    private CourseRepository courseRepository;



    // Show Courses
    @GetMapping("/courses")
    public String courses(Model model, HttpServletRequest request){


        model.addAttribute(
                "courses",
                courseRepository.findAll()
        );

        // SecurityConfig already blocks add/edit/delete routes for
        // students at the server level. This flag just keeps the UI
        // consistent with that, so faculty/students don't see buttons
        // that would 403 if clicked. Course management is admin-only now.
        boolean canManage = request.isUserInRole("ADMIN");

        model.addAttribute("canManage", canManage);


        return "courses";

    }



    // Add Course Page
    @GetMapping("/add-course")
    public String addCourse(Model model){


        model.addAttribute(
                "course",
                new Course()
        );


        return "add-course";

    }



    // Save Course
    @PostMapping("/save-course")
    public String saveCourse(
            @ModelAttribute Course course
    ){


        courseRepository.save(course);


        return "redirect:/courses";

    }



    // Delete Course
    @GetMapping("/delete-course/{id}")
    public String deleteCourse(
            @PathVariable int id
    ){


        courseRepository.deleteById(id);


        return "redirect:/courses";

    }
   
       // Edit Course
@GetMapping("/edit-course/{id}")
public String editCourse(@PathVariable int id, Model model) {

    Course course = courseRepository.findById(id).orElse(null);

    model.addAttribute("course", course);

    return "edit-course";
}


// Update Course
@PostMapping("/update-course")
public String updateCourse(@ModelAttribute Course course) {

    courseRepository.save(course);

    return "redirect:/courses";
}

}