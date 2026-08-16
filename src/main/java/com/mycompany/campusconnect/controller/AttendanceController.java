package com.mycompany.campusconnect.controller;

import com.mycompany.campusconnect.model.Attendance;
import com.mycompany.campusconnect.model.Course;
import com.mycompany.campusconnect.model.Student;
import com.mycompany.campusconnect.repository.AttendanceRepository;
import com.mycompany.campusconnect.repository.CourseRepository;
import com.mycompany.campusconnect.repository.StudentRepository;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
public class AttendanceController {


    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;


    // ================= View all attendance (all roles) =================
    @GetMapping("/attendance")
    public String attendance(Model model, HttpServletRequest request) {

        List<Attendance> attendanceList = attendanceRepository.findAll();

        // Attendance only stores studentId/courseId, so enrich each row
        // with real names for display.
        List<AttendanceRow> rows = new ArrayList<>();

        for (Attendance a : attendanceList) {
            Student student = (a.getStudentId() != null)
                    ? studentRepository.findById(a.getStudentId()).orElse(null)
                    : null;
            Course course = (a.getCourseId() != null)
                    ? courseRepository.findById(a.getCourseId()).orElse(null)
                    : null;

            String studentName = (student != null) ? student.getName() : "Unassigned";
            String courseName = (course != null) ? course.getCourseName() : "Unassigned";

            rows.add(new AttendanceRow(a, studentName, courseName));
        }

        model.addAttribute("attendanceRows", rows);

        boolean canManage = request.isUserInRole("ADMIN") || request.isUserInRole("FACULTY");
        model.addAttribute("canManage", canManage);

        return "attendance";
    }


    // ================= Add Attendance Page (admin + faculty) =================
    @GetMapping("/add-attendance")
    public String addAttendance(Model model) {

        model.addAttribute("attendance", new Attendance());
        model.addAttribute("students", studentRepository.findAll());
        model.addAttribute("courses", courseRepository.findAll());

        return "add-attendance";
    }


    // ================= Save Attendance =================
    @PostMapping("/save-attendance")
    public String saveAttendance(@ModelAttribute Attendance attendance) {

        attendanceRepository.save(attendance);

        return "redirect:/attendance";
    }


    // ================= Edit Attendance Page =================
    @GetMapping("/edit-attendance/{id}")
    public String editAttendance(@PathVariable int id, Model model) {

        Attendance attendance = attendanceRepository.findById(id).orElse(null);

        model.addAttribute("attendance", attendance);
        model.addAttribute("students", studentRepository.findAll());
        model.addAttribute("courses", courseRepository.findAll());

        return "edit-attendance";
    }


    // ================= Update Attendance =================
    @PostMapping("/update-attendance")
    public String updateAttendance(@ModelAttribute Attendance attendance) {

        attendanceRepository.save(attendance);

        return "redirect:/attendance";
    }


    // ================= Delete Attendance =================
    @GetMapping("/delete-attendance/{id}")
    public String deleteAttendance(@PathVariable int id) {

        attendanceRepository.deleteById(id);

        return "redirect:/attendance";
    }


    // Small view-model pairing an Attendance record with real student and
    // course names, since Attendance only stores their IDs.
    public static class AttendanceRow {

        private final Attendance attendance;
        private final String studentName;
        private final String courseName;

        public AttendanceRow(Attendance attendance, String studentName, String courseName) {
            this.attendance = attendance;
            this.studentName = studentName;
            this.courseName = courseName;
        }

        public Attendance getAttendance() {
            return attendance;
        }

        public String getStudentName() {
            return studentName;
        }

        public String getCourseName() {
            return courseName;
        }
    }

}