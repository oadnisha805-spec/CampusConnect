package com.mycompany.campusconnect.controller;

import com.mycompany.campusconnect.model.Fee;
import com.mycompany.campusconnect.model.Student;
import com.mycompany.campusconnect.repository.FeeRepository;
import com.mycompany.campusconnect.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
public class FeeController {


    @Autowired
    private FeeRepository feeRepository;

    @Autowired
    private StudentRepository studentRepository;


    // ================= ADMIN: View all fees =================
    @GetMapping("/fees")
    public String fees(Model model) {

        List<Fee> feeList = feeRepository.findAll();

        // Fee only stores studentId, so enrich each row with the
        // student's name for display rather than storing it twice.
        List<FeeRow> rows = new ArrayList<>();

        for (Fee fee : feeList) {
            Student student = studentRepository.findById(fee.getStudentId()).orElse(null);
            String studentName = (student != null) ? student.getName() : "Unknown student";
            rows.add(new FeeRow(fee, studentName));
        }

        model.addAttribute("feeRows", rows);

        return "fees";
    }


    // ================= ADMIN: Add Fee Page =================
    @GetMapping("/add-fee")
    public String addFee(Model model) {

        model.addAttribute("fee", new Fee());
        model.addAttribute("students", studentRepository.findAll());

        return "add-fee";
    }


    // ================= ADMIN: Save Fee =================
    @PostMapping("/save-fee")
    public String saveFee(@ModelAttribute Fee fee) {

        feeRepository.save(fee);

        return "redirect:/fees";
    }


    // ================= ADMIN: Edit Fee Page =================
    @GetMapping("/edit-fee/{id}")
    public String editFee(@PathVariable int id, Model model) {

        Fee fee = feeRepository.findById(id).orElse(null);

        model.addAttribute("fee", fee);
        model.addAttribute("students", studentRepository.findAll());

        return "edit-fee";
    }


    // ================= ADMIN: Update Fee =================
    @PostMapping("/update-fee")
    public String updateFee(@ModelAttribute Fee fee) {

        feeRepository.save(fee);

        return "redirect:/fees";
    }


    // ================= ADMIN: Delete Fee =================
    @GetMapping("/delete-fee/{id}")
    public String deleteFee(@PathVariable int id) {

        feeRepository.deleteById(id);

        return "redirect:/fees";
    }


    // ================= STUDENT: View own fees =================
    @GetMapping("/my-fees")
    public String myFees(Model model, Authentication authentication) {

        String email = authentication.getName();

        Student student = studentRepository.findByEmail(email);

        List<Fee> myFees = (student != null)
                ? feeRepository.findByStudentId(student.getId())
                : new ArrayList<>();

        model.addAttribute("myFees", myFees);
        model.addAttribute("feesMissing", student == null);

        return "my-fees";
    }


    // Small view-model pairing a Fee with the student's display name,
    // since Fee only stores studentId, not the name itself.
    public static class FeeRow {

        private final Fee fee;
        private final String studentName;

        public FeeRow(Fee fee, String studentName) {
            this.fee = fee;
            this.studentName = studentName;
        }

        public Fee getFee() {
            return fee;
        }

        public String getStudentName() {
            return studentName;
        }
    }

}