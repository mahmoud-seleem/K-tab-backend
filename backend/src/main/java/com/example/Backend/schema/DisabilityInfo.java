package com.example.Backend.schema;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DisabilityInfo {
    private UUID disabilityId;
    private String disabilityName;

    public DisabilityInfo() {
    }

    public DisabilityInfo(UUID disabilityId, String disabilityName) {
        this.disabilityId = disabilityId;
        this.disabilityName = disabilityName;
    }

    public UUID getDisabilityId() {
        return disabilityId;
    }

    public void setDisabilityId(UUID disabilityId) {
        this.disabilityId = disabilityId;
    }

    public String getDisabilityName() {
        return disabilityName;
    }

    public void setDisabilityName(String disabilityName) {
        this.disabilityName = disabilityName;
    }
}
