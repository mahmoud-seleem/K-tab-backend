package com.example.Backend.Repository;

import com.example.Backend.model.AuthorNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthorNotificationRepository extends JpaRepository<AuthorNotification, UUID> {
}
