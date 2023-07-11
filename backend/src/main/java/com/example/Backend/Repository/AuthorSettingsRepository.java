package com.example.Backend.Repository;

import com.example.Backend.model.AuthorSettings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorSettingsRepository extends JpaRepository<AuthorSettings, UUID> {
}
