package com.example.Backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer",
        "handler","student"},ignoreUnknown = true)
@Table(name = "student_settings")
public class StudentSettings {

    @Id
    @GeneratedValue
    @Column(name = "student_settings_id")
    private UUID studentSettingsId;

    @Column(name = "brightness_level")
    private Integer brightnessLevel;

    @Column(name = "contrast_level")
    private Integer contrastLevel;

    @Column(name = "font_size")
    private Integer fontSize;

    @Column(name = "font_style")
    private String fontStyle;

    @Column(name = "invert_color")
    private Boolean invertColor;

    @Column(name = "grayscale")
    private Boolean grayscale;

    @Column(name = "switch_text_to_symbols")
    private Boolean switchTextToSymbols;

    @Column(name = "play_lesson")
    private Boolean playLesson;

    @Column(name = "audio_transcript_highlighting")
    private Boolean audioTranscriptHighLighting;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public StudentSettings() {
        this.brightnessLevel = 0;
        this.contrastLevel = 0;
        this.fontSize = 0;
        this.fontStyle = "fontStyle";
        this.invertColor = true;
        this.grayscale = true;
        this.switchTextToSymbols = true;
        this.playLesson = true;
        this.audioTranscriptHighLighting = true;
    }

    public StudentSettings(int brightnessLevel, int contrastLevel, int fontSize, String fontStyle, boolean invertColor, boolean grayscale, boolean switchTextToSymbols, boolean playLesson, boolean audioTranscriptHighLighting) {
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

    public UUID getStudentSettingsId() {
        return studentSettingsId;
    }

    public void setStudentSettingsId(UUID studentSettingsId) {
        this.studentSettingsId = studentSettingsId;
    }

    public Integer getBrightnessLevel() {
        return brightnessLevel;
    }

    public void setBrightnessLevel(Integer brightnessLevel) {
        this.brightnessLevel = brightnessLevel;
    }

    public Integer getContrastLevel() {
        return contrastLevel;
    }

    public void setContrastLevel(Integer contrastLevel) {
        this.contrastLevel = contrastLevel;
    }

    public Integer getFontSize() {
        return fontSize;
    }

    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(String fontStyle) {
        this.fontStyle = fontStyle;
    }

    public Boolean getInvertColor() {
        return invertColor;
    }

    public void setInvertColor(Boolean invertColor) {
        this.invertColor = invertColor;
    }

    public Boolean getGrayscale() {
        return grayscale;
    }

    public void setGrayscale(Boolean grayscale) {
        this.grayscale = grayscale;
    }

    public Boolean getSwitchTextToSymbols() {
        return switchTextToSymbols;
    }

    public void setSwitchTextToSymbols(Boolean switchTextToSymbols) {
        this.switchTextToSymbols = switchTextToSymbols;
    }

    public Boolean getPlayLesson() {
        return playLesson;
    }

    public void setPlayLesson(Boolean playLesson) {
        this.playLesson = playLesson;
    }

    public Boolean getAudioTranscriptHighLighting() {
        return audioTranscriptHighLighting;
    }

    public void setAudioTranscriptHighLighting(Boolean audioTranscriptHighLighting) {
        this.audioTranscriptHighLighting = audioTranscriptHighLighting;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
