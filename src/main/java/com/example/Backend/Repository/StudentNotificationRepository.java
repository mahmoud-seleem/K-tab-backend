package com.example.Backend.Repository;

import com.example.Backend.model.StudentNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentNotificationRepository extends JpaRepository<StudentNotification, UUID> {
}
