package com.mycompany.campusconnect.controller;

import com.mycompany.campusconnect.model.Course;
import com.mycompany.campusconnect.model.Faculty;
import com.mycompany.campusconnect.model.Notice;
import com.mycompany.campusconnect.model.Student;
import com.mycompany.campusconnect.repository.CourseRepository;
import com.mycompany.campusconnect.repository.FacultyRepository;
import com.mycompany.campusconnect.repository.NoticeRepository;
import com.mycompany.campusconnect.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private NoticeRepository noticeRepository;


    @GetMapping("/admin-dashboard")
    public String adminDashboard(Model model) {

        long studentCount = studentRepository.count();
        long facultyCount = facultyRepository.count();
        long courseCount = courseRepository.count();
        long noticeCount = noticeRepository.count();

        model.addAttribute("studentCount", studentCount);
        model.addAttribute("facultyCount", facultyCount);
        model.addAttribute("courseCount", courseCount);
        model.addAttribute("noticeCount", noticeCount);

        List<ActivityItem> activity = new ArrayList<>();

        for (Student s : studentRepository.findTop5ByOrderByCreatedAtDesc()) {
            activity.add(new ActivityItem("student", s.getName(), "New student registered", s.getCreatedAt()));
        }

        for (Faculty f : facultyRepository.findTop5ByOrderByCreatedAtDesc()) {
            activity.add(new ActivityItem("faculty", f.getName(), "New faculty member added", f.getCreatedAt()));
        }

        for (Course c : courseRepository.findTop5ByOrderByCreatedAtDesc()) {
            activity.add(new ActivityItem("course", c.getCourseName(), "New course created", c.getCreatedAt()));
        }

        for (Notice n : noticeRepository.findTop5ByOrderByCreatedAtDesc()) {
            activity.add(new ActivityItem("notice", n.getTitle(), "Notice published", n.getCreatedAt()));
        }

        activity.sort(Comparator.comparing(
                ActivityItem::getCreatedAt,
                Comparator.nullsLast(Comparator.reverseOrder())
        ));

        if (activity.size() > 5) {
            activity = activity.subList(0, 5);
        }

        model.addAttribute("activity", activity);

        return "admin-dashboard";
    }


    // Small view-model for the merged activity feed. Kept private/inner
    // since it only exists to feed this one dashboard panel.
    public static class ActivityItem {

        private static final DateTimeFormatter DISPLAY_FORMAT =
                DateTimeFormatter.ofPattern("MMM d, h:mm a");

        private final String type;
        private final String name;
        private final String action;
        private final LocalDateTime createdAt;
        private final String displayTime;
        private final String initials;

        public ActivityItem(String type, String name, String action, LocalDateTime createdAt) {
            this.type = type;
            this.name = name;
            this.action = action;
            this.createdAt = createdAt;
            // Formatted here in Java rather than via Thymeleaf's #temporals,
            // since that needs the thymeleaf-extras-java8time dependency
            // which isn't in the project yet.
            this.displayTime = (createdAt != null) ? createdAt.format(DISPLAY_FORMAT) : "Date unknown";
            this.initials = computeInitials(name);
        }

        private static String computeInitials(String name) {
            if (name == null || name.trim().isEmpty()) {
                return "?";
            }
            String[] parts = name.trim().split("\\s+");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < parts.length && i < 2; i++) {
                if (!parts[i].isEmpty()) {
                    sb.append(Character.toUpperCase(parts[i].charAt(0)));
                }
            }
            return sb.length() > 0 ? sb.toString() : "?";
        }

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }

        public String getAction() {
            return action;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public String getDisplayTime() {
            return displayTime;
        }

        public String getInitials() {
            return initials;
        }
    }

}