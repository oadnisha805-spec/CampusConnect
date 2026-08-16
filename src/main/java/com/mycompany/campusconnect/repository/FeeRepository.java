package com.mycompany.campusconnect.repository;

import com.mycompany.campusconnect.model.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FeeRepository extends JpaRepository<Fee, Integer> {

    List<Fee> findByStudentId(int studentId);

}