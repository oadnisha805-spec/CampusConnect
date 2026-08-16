package com.mycompany.campusconnect.controller;

import com.mycompany.campusconnect.model.Assignment;
import com.mycompany.campusconnect.model.Course;
import com.mycompany.campusconnect.repository.AssignmentRepository;
import com.mycompany.campusconnect.repository.CourseRepository;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
public class AssignmentController {


    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private CourseRepository courseRepository;


    // ================= View all assignments (all roles) =================
    @GetMapping("/assignments")
    public String assignments(Model model, HttpServletRequest request) {

        List<Assignment> assignmentList = assignmentRepository.findAll();

        // Assignment only stores courseId, so enrich each row with the
        // course's name for display.
        List<AssignmentRow> rows = new ArrayList<>();

        for (Assignment assignment : assignmentList) {
            Course course = (assignment.getCourseId() != null)
                    ? courseRepository.findById(assignment.getCourseId()).orElse(null)
                    : null;
            String courseName = (course != null) ? course.getCourseName() : "Unassigned";
            rows.add(new AssignmentRow(assignment, courseName));
        }

        model.addAttribute("assignmentRows", rows);

        boolean canManage = request.isUserInRole("ADMIN") || request.isUserInRole("FACULTY");
        model.addAttribute("canManage", canManage);

        return "assignments";
    }


    // ================= Add Assignment Page (admin + faculty) =================
    @GetMapping("/add-assignment")
    public String addAssignment(Model model) {

        model.addAttribute("assignment", new Assignment());
        model.addAttribute("courses", courseRepository.findAll());

        return "add-assignment";
    }


    // ================= Save Assignment =================
    @PostMapping("/save-assignment")
    public String saveAssignment(@ModelAttribute Assignment assignment) {

        assignmentRepository.save(assignment);

        return "redirect:/assignments";
    }


    // ================= Edit Assignment Page =================
    @GetMapping("/edit-assignment/{id}")
    public String editAssignment(@PathVariable int id, Model model) {

        Assignment assignment = assignmentRepository.findById(id).orElse(null);

        model.addAttribute("assignment", assignment);
        model.addAttribute("courses", courseRepository.findAll());

        return "edit-assignment";
    }


    // ================= Update Assignment =================
    @PostMapping("/update-assignment")
    public String updateAssignment(@ModelAttribute Assignment assignment) {

        assignmentRepository.save(assignment);

        return "redirect:/assignments";
    }


    // ================= Delete Assignment =================
    @GetMapping("/delete-assignment/{id}")
    public String deleteAssignment(@PathVariable int id) {

        assignmentRepository.deleteById(id);

        return "redirect:/assignments";
    }


    // Small view-model pairing an Assignment with the course's display
    // name, since Assignment only stores courseId.
    public static class AssignmentRow {

        private final Assignment assignment;
        private final String courseName;

        public AssignmentRow(Assignment assignment, String courseName) {
            this.assignment = assignment;
            this.courseName = courseName;
        }

        public Assignment getAssignment() {
            return assignment;
        }

        public String getCourseName() {
            return courseName;
        }
    }

}