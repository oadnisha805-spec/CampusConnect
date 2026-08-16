package com.mycompany.campusconnect.repository;

import com.mycompany.campusconnect.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {

}