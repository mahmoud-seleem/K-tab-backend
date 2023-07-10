package com.example.Backend.schema;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class DisabilityCount {
    private String disabilityName;
    private int studentsCount;
    private double studentsPercentage;

    public DisabilityCount(String disabilityName, int studentsCount, double studentsPercentage) {
        this.disabilityName = disabilityName;
        this.studentsCount = studentsCount;
        this.studentsPercentage = studentsPercentage;
    }

    public DisabilityCount() {
    }

}
