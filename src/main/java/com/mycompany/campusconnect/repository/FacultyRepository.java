package com.mycompany.campusconnect.repository;

import com.mycompany.campusconnect.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Integer> {

    Faculty findByEmail(String email);

    // For the Recent Activity feed on the admin dashboard
    List<Faculty> findTop5ByOrderByCreatedAtDesc();

}