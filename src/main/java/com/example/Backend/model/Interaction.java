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
    @JoinColumn(name = "reading_id")
    private Reading reading;
    @Convert(converter = JsonConverter.class)
    @Column(columnDefinition = "jsonb")
    @JsonIgnore
    private JSONObject data;

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

    public Reading getReading() {
        return reading;
    }

    public void setReading(Reading reading) {
        this.reading = reading;
    }
    //Reference for below code: https://stackoverflow.com/questions/21720759/convert-a-json-string-to-a-hashmap

}
