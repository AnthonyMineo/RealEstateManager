package com.openclassrooms.realestatemanager.models.local.immovables;


public class Picture {

    private String place;
    private String fileName;
    private String uri;

    // --- DEFAULT CONSTRUCTOR ---
    public Picture(){}

    // --- COMPLETE CONSTRUCTOR ---
    public Picture(String place, String fileName, String uri) {
        this.place = place;
        this.fileName = fileName;
        this.uri = uri;
    }

    // --- GETTER ---
    public String getPlace() { return place; }
    public String getFileName() { return fileName; }
    public String getUri() {
        return uri;
    }


    // --- SETTER ---
    public void setPlace(String place) { this.place = place; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public void setUri(String uri) {
        this.uri = uri;
    }
}
