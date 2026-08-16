package com.mycompany.campusconnect.controller;


import com.mycompany.campusconnect.model.Student;
import com.mycompany.campusconnect.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class StudentController {


    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // Student Profile Page
    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {

        String email = authentication.getName();

        Student student = studentRepository.findByEmail(email);

        model.addAttribute("student", student);
        model.addAttribute("profileMissing", student == null);

        return "profile";
    }


    // Student Dashboard
    @GetMapping("/student-dashboard")
    public String dashboard() {

        return "student-dashboard";

    }

    // Admin - View All Students
    @GetMapping("/students")
    public String students(Model model) {

        model.addAttribute("students", studentRepository.findAll());

        return "students";
    }


    // Admin - Add Student Page
    @GetMapping("/add-student")
    public String addStudent(Model model) {

        model.addAttribute("student", new Student());

        return "add-student";
    }


    // Admin - Save Student
    @PostMapping("/save-student")
    public String saveStudent(@ModelAttribute Student student) {

        hashPasswordIfNeeded(student);
        studentRepository.save(student);

        return "redirect:/students";
    }


    // Admin - Delete Student
    @GetMapping("/delete-student/{id}")
    public String deleteStudent(@PathVariable int id) {

        studentRepository.deleteById(id);

        return "redirect:/students";
    }

    // Admin - Edit Student Page
    @GetMapping("/edit-student/{id}")
    public String editStudent(@PathVariable int id, Model model) {

        Student student = studentRepository.findById(id).orElse(null);

        model.addAttribute("student", student);

        return "edit-student";
    }


    // Admin - Update Student
    @PostMapping("/update-student")
    public String updateStudent(@ModelAttribute Student student) {

        hashPasswordIfNeeded(student);
        studentRepository.save(student);

        return "redirect:/students";
    }


    // Hashes the password only if it isn't already a hash. Since
    // edit-student.html shows the current (possibly already-hashed) value
    // in the form, submitting the form unchanged would re-hash an
    // already-hashed string if we didn't check first - this guard avoids
    // that "double hashing" bug. Encoded values always start with "{"
    // (e.g. "{bcrypt}...").
    private void hashPasswordIfNeeded(Student student) {
        String pw = student.getPassword();
        if (pw != null && !pw.startsWith("{")) {
            student.setPassword(passwordEncoder.encode(pw));
        }
    }

}