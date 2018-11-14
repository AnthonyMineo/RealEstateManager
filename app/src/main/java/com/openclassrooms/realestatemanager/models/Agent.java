package com.openclassrooms.realestatemanager.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Agent {

    @PrimaryKey
    private int id;
    private String userName;
    private String mailAddress;
    private String urlPicture;

    public Agent (int id, String userName, String mailAddress, String urlPicture){
        this.id = id;
        this.userName = userName;
        this.mailAddress = mailAddress;
        this.urlPicture = urlPicture;
    }

    // --- GETTER ---
    public int getId() { return id; }
    public String getUserName() { return userName; }
    public String getMailAddress() { return mailAddress; }
    public String getUrlPicture() { return urlPicture; }

    // --- SETTER ---
    public void setId(int id) { this.id = id; }
    public void setUserName(String userName) { this.userName = userName; }
    public void setMailAddress(String mailAddress) { this.mailAddress = mailAddress; }
    public void setUrlPicture(String urlPicture) { this.urlPicture = urlPicture; }
}
