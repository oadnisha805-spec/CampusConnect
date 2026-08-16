package com.mycompany.campusconnect.repository;

import com.mycompany.campusconnect.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

}