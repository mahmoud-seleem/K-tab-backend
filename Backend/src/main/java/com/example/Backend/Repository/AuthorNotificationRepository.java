package com.example.Backend.Repository;

import com.example.Backend.model.AuthorNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorNotificationRepository extends JpaRepository<AuthorNotification, UUID> {
}
