package com.mycompany.campusconnect.repository;


import com.mycompany.campusconnect.model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer> {

    // For the Recent Activity feed on the admin dashboard
    List<Notice> findTop5ByOrderByCreatedAtDesc();

}