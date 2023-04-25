package com.example.Backend.model;
import com.example.Backend.jsonConversion.JsonConverter;
import com.example.Backend.jsonConversion.JsonToMapConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

@Entity
@Table(name = "interaction")
public class Interaction {

    @Id
    @GeneratedValue
    @Column(name = "interaction_id",nullable = false)
    private UUID interactionId;

    @ManyToOne
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @Convert(converter = JsonConverter.class)
    @Column(columnDefinition = "jsonb")
    @JsonIgnore
    private JSONObject data;

    @Column(name = "reading_progress" , nullable = false)
    private int readingProgress;
    @JsonProperty("data")
    public Map<String, Object> getAsJsonString() throws IOException, JSONException {
        return JsonToMapConverter.toMap(data);
    }
    public Interaction() {
    }


    public Interaction(JSONObject data) {
        this.data = data;
    }

    public UUID getInteractionId() {
        return interactionId;
    }

    public void setInteractionId(UUID interactionId) {
        this.interactionId = interactionId;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getReadingProgress() {
        return readingProgress;
    }

    public void setReadingProgress(int readingProgress) {
        this.readingProgress = readingProgress;
    }
    //Reference for below code: https://stackoverflow.com/questions/21720759/convert-a-json-string-to-a-hashmap

}
