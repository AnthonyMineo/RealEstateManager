package com.openclassrooms.realestatemanager.models.local.immovables;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.openclassrooms.realestatemanager.models.local.Agent;

import java.util.ArrayList;
import java.util.List;


@Entity(foreignKeys = @ForeignKey(entity = Agent.class,
        parentColumns = "id",
        childColumns = "agentId"))
public class Immo {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String type;
    private int price;
    private int surface;
    private int pieceNumber;
    private int bathNumber;
    private int bedNumber;
    private String description;
    private List<Picture> gallery = new ArrayList<Picture>();
    private Vicinity vicinity;
    private List<String> pointsOfInterest = new ArrayList<String>();
    private boolean status;
    private String enterDate;
    private String sellingDate; // omitted in constructor because it should be initialize during an update
    private long agentId;

    // --- DEFAULT CONSTRUCTOR ---
    public Immo() {}

    // --- PARTIAL CONSTRUCTORS ---
    // without "gallery" / "pointsOfInterest"
    public Immo(String type, int price, int surface, int pieceNumber, int bathNumber, int bedNumber, String description, Vicinity vicinity,
                String enterDate, long agentId) {
        this.type = type;
        this.price = price;
        this.surface = surface;
        this.pieceNumber = pieceNumber;
        this.bathNumber = bathNumber;
        this.bedNumber = bedNumber;
        this.description = description;
        this.vicinity = vicinity;
        this.status = false;
        this.enterDate = enterDate;
        this.agentId = agentId;
    }

    // --- COMPLETE CONSTRUCTOR ---
    public Immo(String type, int price, int surface, int pieceNumber, int bathNumber, int bedNumber, String description, List<Picture> gallery,
                Vicinity vicinity, List<String> pointsOfInterest, String enterDate, long agentId) {
        this.type = type;
        this.price = price;
        this.surface = surface;
        this.pieceNumber = pieceNumber;
        this.bathNumber = bathNumber;
        this.bedNumber = bedNumber;
        this.description = description;
        this.gallery = gallery;
        this.vicinity = vicinity;
        this.pointsOfInterest = pointsOfInterest;
        this.status = false;
        this.enterDate = enterDate;
        this.agentId = agentId;
    }

    // --- GETTER ---
    public long getId() { return id; }
    public String getType() { return type; }
    public int getPrice() { return price; }
    public int getSurface() { return surface; }
    public int getPieceNumber() { return pieceNumber; }
    public int getBathNumber() { return bathNumber; }
    public int getBedNumber() { return bedNumber; }
    public String getDescription() { return description; }
    public List<Picture> getGallery() { return gallery; }
    public Vicinity getVicinity() { return vicinity; }
    public List<String> getPointsOfInterest() { return pointsOfInterest; }
    public boolean isStatus() { return status; }
    public String getEnterDate() { return enterDate; }
    public String getSellingDate() { return sellingDate; }
    public long getAgentId() { return agentId; }

    // --- SETTER ---
    public void setId(long id) { this.id = id; }
    public void setType(String type) { this.type = type; }
    public void setPrice(int price) { this.price = price; }
    public void setSurface(int surface) { this.surface = surface; }
    public void setPieceNumber(int pieceNumber) { this.pieceNumber = pieceNumber; }
    public void setBathNumber(int bathNumber) { this.bathNumber = bathNumber; }
    public void setBedNumber(int bedNumber) { this.bedNumber = bedNumber; }
    public void setDescription(String description) { this.description = description; }
    public void setGallery(List<Picture> gallery) { this.gallery = gallery; }
    public void setVicinity(Vicinity vicinity) { this.vicinity = vicinity; }
    public void setPointsOfInterest(List<String> pointsOfInterest) { this.pointsOfInterest = pointsOfInterest; }
    public void setStatus(boolean status) { this.status = status; }
    public void setEnterDate(String enterDate) { this.enterDate = enterDate; }
    public void setSellingDate(String sellingDate) { this.sellingDate = sellingDate; }
    public void setAgentId(long agentId) { this.agentId = agentId; }

    // --- UTILS ---

    /*
    To do : method that transform ContentValues to Immo object in order to work with our futur ContentProvider
     */
}
