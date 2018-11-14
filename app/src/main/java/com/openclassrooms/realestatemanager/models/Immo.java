package com.openclassrooms.realestatemanager.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Entity(foreignKeys = @ForeignKey(entity = Agent.class,
        parentColumns = "id",
        childColumns = "agentId"))
public class Immo {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String type;
    private int price;
    private int surface;
    private int pieceNumber;
    private String description;
    private Map<String, String> pathAndFileNameForPicture = new HashMap<String, String>();
    private String address;
    private List<String> pointOfInterest = new ArrayList<String>();
    private boolean status;
    private String enterDate;
    private String sellingDate;
    private int agentId;

    // --- DEFAULT CONSTRUCTOR ---
    public Immo() {}

    // --- PARTIAL CONSTRUCTOR ---
    public Immo(String type, int price, int surface, int pieceNumber, String address, Boolean status, String enterDate, int agentId) {
        this.type = type;
        this.price = price;
        this.surface = surface;
        this.pieceNumber = pieceNumber;
        this.address = address;
        this.status = status;
        this.enterDate = enterDate;
        this.agentId = agentId;
    }

    // --- COMPLETE CONSTRUCTOR ---
    public Immo(String type, int price, int surface, int pieceNumber, String description, Map<String, String> pathAndFileNameForPicture,
                String address, List<String> pointOfInterest, Boolean status, String enterDate, String sellingDate, int agentId) {
        this.type = type;
        this.price = price;
        this.surface = surface;
        this.pieceNumber = pieceNumber;
        this.description = description;
        this.pathAndFileNameForPicture = pathAndFileNameForPicture;
        this.address = address;
        this.pointOfInterest = pointOfInterest;
        this.status = status;
        this.enterDate = enterDate;
        this.sellingDate = sellingDate;
        this.agentId = agentId;
    }

    // --- GETTER ---
    public int getId() { return id; }
    public String getType() { return type; }
    public int getPrice() { return price; }
    public int getSurface() { return surface; }
    public int getPieceNumber() { return pieceNumber; }
    public String getDescription() { return description; }
    public Map<String, String> getPathAndFileNameForPicture() { return pathAndFileNameForPicture; }
    public String getAddress() { return address; }
    public List<String> getPointOfInterest() { return pointOfInterest; }
    public boolean isStatus() { return status; }
    public String getEnterDate() { return enterDate; }
    public String getSellingDate() { return sellingDate; }
    public int getAgentId() { return agentId; }

    // --- SETTER ---
    public void setId(int id) { this.id = id; }
    public void setType(String type) { this.type = type; }
    public void setPrice(int price) { this.price = price; }
    public void setSurface(int surface) { this.surface = surface; }
    public void setPieceNumber(int pieceNumber) { this.pieceNumber = pieceNumber; }
    public void setDescription(String description) { this.description = description; }
    public void setPathAndFileNameForPicture(Map<String, String> pathAndFileNameForPicture) { this.pathAndFileNameForPicture = pathAndFileNameForPicture; }
    public void setAddress(String address) { this.address = address; }
    public void setPointOfInterest(List<String> pointOfInterest) { this.pointOfInterest = pointOfInterest; }
    public void setStatus(boolean status) { this.status = status; }
    public void setEnterDate(String enterDate) { this.enterDate = enterDate; }
    public void setSellingDate(String sellingDate) { this.sellingDate = sellingDate; }
    public void setAgentId(int agentId) { this.agentId = agentId; }

    // --- UTILS ---

    /*
    To do : method that transform ContentValues to Immo object in order to work with our futur ContentProvider
     */
}
