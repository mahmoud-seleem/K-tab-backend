package com.example.Backend.Repository;

import com.example.Backend.model.Disability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DisabilityRepository extends JpaRepository<Disability, UUID> {
}
