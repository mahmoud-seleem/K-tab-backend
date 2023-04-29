package com.example.Backend.jsonConversion;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Converter(autoApply = true)
@Component
public class JsonConverter implements AttributeConverter<JSONObject, String> {
    @Override
    public String convertToDatabaseColumn(JSONObject jsonObject) {
        return jsonObject.toString();
    }

    @Override
    public JSONObject convertToEntityAttribute(String s) {
        return new JSONObject(s);
    }
}
