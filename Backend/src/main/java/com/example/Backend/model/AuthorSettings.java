package com.example.Backend.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "author_settings")
public class AuthorSettings {

    @Id
    @GeneratedValue
    @Column(name = "setting_id")
    UUID settingId;

    @Column(name = "brightness_level")
    int brightnessLevel;

    @Column(name = "contrast_level")
    int contrastLevel;

    @Column(name = "font_size")
    int fontSize;

    @Column(name = "font_style")
    String fontStyle;

    @Column(name = "invert_color")
    Boolean invertColor;

    @Column(name = "grayscale")
    boolean grayscale;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private Author author;

    public UUID getSettingId() {
        return settingId;
    }

    public void setSettingId(UUID settingId) {
        this.settingId = settingId;
    }

    public int getBrightnessLevel() {
        return brightnessLevel;
    }

    public void setBrightnessLevel(int brightnessLevel) {
        this.brightnessLevel = brightnessLevel;
    }

    public int getContrastLevel() {
        return contrastLevel;
    }

    public void setContrastLevel(int contrastLevel) {
        this.contrastLevel = contrastLevel;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
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

    public boolean isGrayscale() {
        return grayscale;
    }

    public void setGrayscale(boolean grayscale) {
        this.grayscale = grayscale;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
