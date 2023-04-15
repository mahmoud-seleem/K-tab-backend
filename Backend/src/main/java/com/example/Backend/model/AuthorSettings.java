package com.example.Backend.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "author_settings")
public class AuthorSettings {

    @Id
    @GeneratedValue
    @Column(name = "author_settings_id")
    private UUID authorSettingsId;

    @Column(name = "brightness_level")
    private int brightnessLevel;

    @Column(name = "contrast_level")
    private int contrastLevel;

    @Column(name = "font_size")
    private int fontSize;

    @Column(name = "font_style")
    private String fontStyle;

    @Column(name = "invert_color")
    private boolean invertColor;

    @Column(name = "grayscale")
    private boolean grayscale;

    @OneToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public AuthorSettings() {
        this.brightnessLevel = 0;
        this.contrastLevel =0 ;
        this.fontSize = 0;
        this.fontStyle = "fontStyle";
        this.invertColor =true ;
        this.grayscale = true;
    }

    public AuthorSettings(int brightnessLevel, int contrastLevel, int fontSize, String fontStyle, boolean invertColor, boolean grayscale) {
        this.brightnessLevel = brightnessLevel;
        this.contrastLevel = contrastLevel;
        this.fontSize = fontSize;
        this.fontStyle = fontStyle;
        this.invertColor = invertColor;
        this.grayscale = grayscale;
    }


    public UUID getAuthorSettingsId() {
        return authorSettingsId;
    }

    public void setAuthorSettingsId(UUID authorSettingsId) {
        this.authorSettingsId = authorSettingsId;
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

    public boolean getInvertColor() {
        return invertColor;
    }

    public void setInvertColor(boolean invertColor) {
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
