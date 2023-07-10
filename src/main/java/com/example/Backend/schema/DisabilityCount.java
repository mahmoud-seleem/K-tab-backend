package com.example.Backend.schema;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class DisabilityCount {
    private String disabilityName;
    private int studentsCount;
    private String studentsPercentage;

    public DisabilityCount(String disabilityName, int studentsCount, String studentsPercentage) {
        this.disabilityName = disabilityName;
        this.studentsCount = studentsCount;
        this.studentsPercentage = studentsPercentage;
    }

    public DisabilityCount(String disabilityName) {
        this.disabilityName = disabilityName;
        this.studentsCount = 0;
        this.studentsPercentage = null;
    }

    public DisabilityCount() {
    }

}
