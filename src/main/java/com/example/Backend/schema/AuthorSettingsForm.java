package com.example.Backend.schema;

import com.example.Backend.model.Book;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.UUID;

public class AuthorSettingsForm {
    private UUID authorId;
    private UUID authorSettingsId;
    private Integer brightnessLevel;
    private Integer contrastLevel;
    private Integer fontSize;
    private String fontStyle;
    private Boolean invertColor;
    private Boolean grayScale;

    public AuthorSettingsForm() {
    }

    public AuthorSettingsForm(UUID authorId, UUID authorSettingsId, Integer brightnessLevel, Integer contrastLevel, Integer fontSize, String fontStyle, Boolean invertColor, Boolean grayscale) {
        this.authorId = authorId;
        this.authorSettingsId = authorSettingsId;
        this.brightnessLevel = brightnessLevel;
        this.contrastLevel = contrastLevel;
        this.fontSize = fontSize;
        this.fontStyle = fontStyle;
        this.invertColor = invertColor;
        this.grayScale = grayscale;
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
}

