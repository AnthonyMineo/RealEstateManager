package com.openclassrooms.realestatemanager.models.local.immovables;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.openclassrooms.realestatemanager.models.local.Agent;

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
    private List<Picture> gallery = new ArrayList<Picture>();
    private Vicinity vicinity;
    private List<String> pointOfInterest = new ArrayList<String>();
    private boolean status;
    private String enterDate;
    private String sellingDate;
    private int agentId;

    // --- DEFAULT CONSTRUCTOR ---
    public Immo() {}

    // --- PARTIAL CONSTRUCTOR ---
    public Immo(String type, int price, int surface, int pieceNumber, Vicinity vicinity, Boolean status, String enterDate, int agentId) {
        this.type = type;
        this.price = price;
        this.surface = surface;
        this.pieceNumber = pieceNumber;
        this.vicinity = vicinity;
        this.status = status;
        this.enterDate = enterDate;
        this.agentId = agentId;
    }

    // --- COMPLETE CONSTRUCTOR ---
    public Immo(String type, int price, int surface, int pieceNumber, String description, List<Picture> gallery,
                Vicinity vicinity, List<String> pointOfInterest, Boolean status, String enterDate, String sellingDate, int agentId) {
        this.type = type;
        this.price = price;
        this.surface = surface;
        this.pieceNumber = pieceNumber;
        this.description = description;
        this.gallery = gallery;
        this.vicinity = vicinity;
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
    public List<Picture> getGallery() { return gallery; }
    public Vicinity getVicinity() { return vicinity; }
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
    public void setGallery(List<Picture> gallery) { this.gallery = gallery; }
    public void setVicinity(Vicinity vicinity) { this.vicinity = vicinity; }
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
