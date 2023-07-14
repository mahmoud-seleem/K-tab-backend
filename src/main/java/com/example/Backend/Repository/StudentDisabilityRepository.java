package com.example.Backend.Repository;

import com.example.Backend.model.StudentDisability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentDisabilityRepository extends JpaRepository<StudentDisability, UUID> {
}
