package com.openclassrooms.realestatemanager.models.local.immovables;

import android.net.Uri;

public class Picture {

    private String place;
    private String path;
    private String fileName;
    private String uri;

    // --- DEFAULT CONSTRUCTOR ---
    public Picture(){}

    // --- COMPLETE CONSTRUCTOR ---
    public Picture(String place, String path, String fileName, String uri) {
        this.place = place;
        this.path = path;
        this.fileName = fileName;
        this.uri = uri;
    }

    // --- GETTER ---
    public String getPlace() { return place; }
    public String getPath() { return path; }
    public String getFileName() { return fileName; }
    public String getUri() {
        return uri;
    }


    // --- SETTER ---
    public void setPlace(String place) { this.place = place; }
    public void setPath(String path) { this.path = path; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public void setUri(String uri) {
        this.uri = uri;
    }
}
