package com.example.Backend.Repository;

import com.example.Backend.model.StudentDisability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentDisabilityRepository extends JpaRepository<StudentDisability, UUID> {
}
