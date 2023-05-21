package com.example.Backend.schema;

import java.util.ArrayList;
import java.util.List;

public class SettingsResponse {
    private List<SettingsElement> settings = new ArrayList<>();

    public SettingsResponse() {
    }

    public SettingsResponse(List<SettingsElement> settings) {
        this.settings = settings;
    }

    public List<SettingsElement> getSettings() {
        return settings;
    }

    public void setSettings(List<SettingsElement> settings) {
        this.settings = settings;
    }
}
