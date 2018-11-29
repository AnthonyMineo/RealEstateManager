package com.openclassrooms.realestatemanager.models.local.immovables;

public class Picture {

    private String place;
    private String path;
    private String fileName;

    // --- DEFAULT CONSTRUCTOR ---
    public Picture(){}

    // --- COMPLETE CONSTRUCTOR ---
    public Picture(String place, String path, String fileName) {
        this.place = place;
        this.path = path;
        this.fileName = fileName;
    }

    // --- GETTER ---
    public String getPlace() { return place; }
    public String getPath() { return path; }
    public String getFileName() { return fileName; }

    // --- SETTER ---
    public void setPlace(String place) { this.place = place; }
    public void setPath(String path) { this.path = path; }
    public void setFileName(String fileName) { this.fileName = fileName; }
}
