package com.mycompany.campusconnect.controller;


import com.mycompany.campusconnect.model.Notice;
import com.mycompany.campusconnect.repository.NoticeRepository;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class NoticeController {


    @Autowired
    private NoticeRepository noticeRepository;



    @GetMapping("/notices")
    public String showNotices(Model model, HttpServletRequest request){


        model.addAttribute(
                "notices",
                noticeRepository.findAll()
        );

        // Same pattern as CourseController: SecurityConfig already blocks
        // add/edit/delete routes for students server-side, this just keeps
        // the UI consistent with that.
        boolean canManage = request.isUserInRole("ADMIN")
                || request.isUserInRole("FACULTY");

        model.addAttribute("canManage", canManage);


        return "notices";
    }


    @GetMapping("/add-notice")
    public String addNotice(Model model){

        model.addAttribute(
                "notice",
                new Notice()
        );

        return "add-notice";
    }



    @PostMapping("/save-notice")
    public String saveNotice(@ModelAttribute Notice notice){

        noticeRepository.save(notice);

        return "redirect:/notices";
    }

       // Edit Notice Page
@GetMapping("/edit-notice/{id}")
public String editNotice(@PathVariable int id, Model model) {

    Notice notice = noticeRepository.findById(id).orElse(null);

    model.addAttribute("notice", notice);

    return "edit-notice";
}
        // Update Notice
@PostMapping("/update-notice")
public String updateNotice(@ModelAttribute Notice notice) {

    noticeRepository.save(notice);

    return "redirect:/notices";
} 
     
      // Delete Notice
@GetMapping("/delete-notice/{id}")
public String deleteNotice(@PathVariable int id) {

    noticeRepository.deleteById(id);

    return "redirect:/notices";
}
}