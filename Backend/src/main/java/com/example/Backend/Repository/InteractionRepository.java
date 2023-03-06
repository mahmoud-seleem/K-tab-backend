package com.example.Backend.Repository;

import com.example.Backend.model.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InteractionRepository extends JpaRepository<Interaction, UUID> {
}
