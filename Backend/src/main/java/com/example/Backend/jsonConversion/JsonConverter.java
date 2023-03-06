package com.example.Backend.jsonConversion;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.json.JSONObject;
@Converter(autoApply = true)
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
