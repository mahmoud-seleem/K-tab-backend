package com.example.Backend.Repository;

import com.example.Backend.model.StudentSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentSettingsRepository extends JpaRepository<StudentSettings, UUID> {
}
