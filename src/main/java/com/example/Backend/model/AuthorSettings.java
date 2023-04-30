package com.example.Backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "author_settings")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorSettings {

    @Id
    @GeneratedValue
    @Column(name = "author_settings_id")
    private UUID authorSettingsId;

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

    @Column(name = "gray_Scale")
    private Boolean grayScale;

    @OneToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public AuthorSettings() {
        this.brightnessLevel = 10;
        this.contrastLevel = 10;
        this.fontSize = 10;
        this.fontStyle = "Times new Roman";
        this.invertColor = false;
        this.grayScale = false;
    }

    public AuthorSettings(Integer brightnessLevel, Integer contrastLevel, Integer fontSize, String fontStyle, Boolean invertColor, Boolean grayScale, Author author) {
        this.brightnessLevel = brightnessLevel;
        this.contrastLevel = contrastLevel;
        this.fontSize = fontSize;
        this.fontStyle = fontStyle;
        this.invertColor = invertColor;
        this.grayScale = grayScale;
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
