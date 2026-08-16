package com.mycompany.campusconnect.repository;


import com.mycompany.campusconnect.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    // For the Recent Activity feed on the admin dashboard
    List<Course> findTop5ByOrderByCreatedAtDesc();

}