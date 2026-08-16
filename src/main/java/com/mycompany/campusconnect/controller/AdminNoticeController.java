package com.mycompany.campusconnect.controller;


import com.mycompany.campusconnect.model.Notice;
import com.mycompany.campusconnect.repository.NoticeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class AdminNoticeController {


    @Autowired
    private NoticeRepository noticeRepository;



    @GetMapping("/admin/add-notice")
    public String addNoticePage(){

        return "add-notice";

    }



    @PostMapping("/admin/add-notice")
    public String saveNotice(Notice notice, Model model){


        noticeRepository.save(notice);


        model.addAttribute(
                "message",
                "Notice Published Successfully!"
        );


        return "notice-success";

    }

}