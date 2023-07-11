package com.example.Backend.Repository;

import com.example.Backend.model.StudentNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentNotificationRepository extends JpaRepository<StudentNotification, UUID> {
}
