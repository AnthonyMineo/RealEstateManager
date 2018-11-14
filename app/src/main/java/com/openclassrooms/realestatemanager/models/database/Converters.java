package com.openclassrooms.realestatemanager.models.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Converters {
    static Gson gson = new Gson();

    // --- FOR LIST ---
    @TypeConverter
    public static List<String> stringToListString(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        Type listType = new TypeToken<List<String>>() {}.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String ListStringToString(List<String> stringList) {
        return gson.toJson(stringList);
    }

    // --- FOR MAP ---
    @TypeConverter
    public static Map<String, String> stringToMapString(String data) {
        if (data == null) {
            return Collections.emptyMap();
        }
        Type mapType = new TypeToken<Map<String, String>>() {}.getType();
        return gson.fromJson(data, mapType);
    }

    @TypeConverter
    public static String MapStringToString(Map<String, String> stringMap) {
        return gson.toJson(stringMap);
    }

}
