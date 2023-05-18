package com.example.Backend.schema;

import jakarta.persistence.Column;

import java.util.UUID;

public class StudentSettingsForm {

    private UUID studentId;

    private UUID studentSettingsId;

    private Integer brightnessLevel;

    private Integer contrastLevel;

    private Integer fontSize;

    private String fontStyle;

    private Boolean invertColor;

    private Boolean grayscale;

    private Boolean switchTextToSymbols;

    private Boolean playLesson;

    private Boolean audioTranscriptHighLighting;

    public StudentSettingsForm() {
    }

    public StudentSettingsForm(UUID studentId, UUID studentSettingsId, Integer brightnessLevel, Integer contrastLevel, Integer fontSize, String fontStyle, Boolean invertColor, Boolean grayscale, Boolean switchTextToSymbols, Boolean playLesson, Boolean audioTranscriptHighLighting) {
        this.studentId = studentId;
        this.studentSettingsId = studentSettingsId;
        this.brightnessLevel = brightnessLevel;
        this.contrastLevel = contrastLevel;
        this.fontSize = fontSize;
        this.fontStyle = fontStyle;
        this.invertColor = invertColor;
        this.grayscale = grayscale;
        this.switchTextToSymbols = switchTextToSymbols;
        this.playLesson = playLesson;
        this.audioTranscriptHighLighting = audioTranscriptHighLighting;
    }
}
