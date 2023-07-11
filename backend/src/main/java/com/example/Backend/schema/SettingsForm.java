package com.example.Backend.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SettingsForm {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID authorId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID authorSettingsId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID studentId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID studentSettingsId;
    @Min(value = 0,message = "brightnessLevel can't be less than 0")
    @Max(value = 10,message = "brightnessLevel can't be more than 10")
    private Integer brightnessLevel;
    @Min(value = 0,message = "contrastLevel can't be less than 0")
    @Max(value = 10,message = "contrastLevel can't be more than 10")
    private Integer contrastLevel;
    @Min(value = 0,message = "fontSize can't be less than 0")
    @Max(value = 200,message = "fontSize can't be more than 200")
    private Integer fontSize;
    private String fontStyle;
    private Boolean invertColor;
    private Boolean grayScale;
    private Boolean switchTextToSymbols;
    private Boolean playLesson;
    private Boolean audioTranscriptHighLighting;

    public SettingsForm() {
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }

    public UUID getAuthorSettingsId() {
        return authorSettingsId;
    }

    public void setAuthorSettingsId(UUID authorSettingsId) {
        this.authorSettingsId = authorSettingsId;
    }

    public UUID getStudentId() {
        return studentId;
    }

    public void setStudentId(UUID studentId) {
        this.studentId = studentId;
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

    public Boolean getGrayScale() {
        return grayScale;
    }

    public void setGrayScale(Boolean grayScale) {
        this.grayScale = grayScale;
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
}
