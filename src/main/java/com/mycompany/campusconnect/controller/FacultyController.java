package com.mycompany.campusconnect.controller;

import com.mycompany.campusconnect.model.Faculty;
import com.mycompany.campusconnect.repository.FacultyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class FacultyController {

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Faculty Dashboard
    @GetMapping("/faculty-dashboard")
    public String facultyDashboard() {
        return "faculty-dashboard";
    }

    // View Faculty
    @GetMapping("/faculty")
    public String faculty(Model model) {

        model.addAttribute("faculties", facultyRepository.findAll());

        return "faculty";
    }

    // Add Faculty Page
    @GetMapping("/add-faculty")
    public String addFaculty(Model model) {

        model.addAttribute("faculty", new Faculty());

        return "add-faculty";
    }

    // Save Faculty
    @PostMapping("/save-faculty")
    public String saveFaculty(@ModelAttribute Faculty faculty) {

        hashPasswordIfNeeded(faculty);
        facultyRepository.save(faculty);

        return "redirect:/faculty";
    }

    // Edit Faculty
    @GetMapping("/edit-faculty/{id}")
    public String editFaculty(@PathVariable int id, Model model) {

        Faculty faculty = facultyRepository.findById(id).orElse(null);

        model.addAttribute("faculty", faculty);

        return "edit-faculty";
    }

    // Update Faculty
    @PostMapping("/update-faculty")
    public String updateFaculty(@ModelAttribute Faculty faculty) {

        hashPasswordIfNeeded(faculty);
        facultyRepository.save(faculty);

        return "redirect:/faculty";
    }

    // Delete Faculty
    @GetMapping("/delete-faculty/{id}")
    public String deleteFaculty(@PathVariable int id) {

        facultyRepository.deleteById(id);

        return "redirect:/faculty";
    }

    // Same double-hash guard as StudentController - only hash if the value
    // isn't already an encoded hash (encoded values start with "{").
    private void hashPasswordIfNeeded(Faculty faculty) {
        String pw = faculty.getPassword();
        if (pw != null && !pw.startsWith("{")) {
            faculty.setPassword(passwordEncoder.encode(pw));
        }
    }

}