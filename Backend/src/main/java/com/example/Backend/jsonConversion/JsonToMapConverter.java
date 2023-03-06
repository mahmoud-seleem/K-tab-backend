package com.example.Backend.jsonConversion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class JsonToMapConverter {
    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if(value instanceof JSONArray) {
                value = JsonToMapConverter.toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = JsonToMapConverter.toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for(int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if(value instanceof JSONArray) {
                value = JsonToMapConverter.toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = JsonToMapConverter.toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }
}
