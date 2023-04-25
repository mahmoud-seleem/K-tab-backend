package com.example.Backend.Repository;

import com.example.Backend.compositeKeys.ContributionKey;
import com.example.Backend.model.Contribution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContributionRepository extends JpaRepository<Contribution, ContributionKey> {
}
