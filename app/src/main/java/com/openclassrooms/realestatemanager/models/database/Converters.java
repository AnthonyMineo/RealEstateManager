package com.openclassrooms.realestatemanager.models.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.openclassrooms.realestatemanager.models.local.immovables.Immo;
import com.openclassrooms.realestatemanager.models.local.immovables.Picture;
import com.openclassrooms.realestatemanager.models.local.immovables.Vicinity;

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

    // --- FOR IMMO VICINITY ---
    @TypeConverter
    public static Vicinity stringToVicinity(String data) {
        if (data == null) {
            return new Vicinity();
        }
        Type locationType = new TypeToken<Vicinity>() {}.getType();
        return gson.fromJson(data, locationType);
    }

    @TypeConverter
    public static String VicinityToString(Vicinity location) {
        return gson.toJson(location);
    }

    // --- FOR IMMO PICTURE ---
    @TypeConverter
    public static Picture stringToPicture(String data) {
        if (data == null) {
            return new Picture();
        }
        Type locationType = new TypeToken<Picture>() {}.getType();
        return gson.fromJson(data, locationType);
    }

    @TypeConverter
    public static String PictureToString(Picture pic) {
        return gson.toJson(pic);
    }

    // --- FOR IMMO GALLERY ---
    @TypeConverter
    public static List<Picture> stringToGallery(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        Type locationType = new TypeToken<List<Picture>>() {}.getType();
        return gson.fromJson(data, locationType);
    }

    @TypeConverter
    public static String GalleryToString(List<Picture> gal) {
        return gson.toJson(gal);
    }


}
