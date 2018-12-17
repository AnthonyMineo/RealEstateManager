package com.openclassrooms.realestatemanager.models.local;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Agent {

    @PrimaryKey
    private long id;
    private String userName;
    private String mailAddress;
    private String urlPicture;

    public Agent(){}

    public Agent (long id, String userName, String mailAddress, String urlPicture){
        this.id = id;
        this.userName = userName;
        this.mailAddress = mailAddress;
        this.urlPicture = urlPicture;
    }

    // --- GETTER ---
    public long getId() { return id; }
    public String getUserName() { return userName; }
    public String getMailAddress() { return mailAddress; }
    public String getUrlPicture() { return urlPicture; }

    // --- SETTER ---
    public void setId(long id) { this.id = id; }
    public void setUserName(String userName) { this.userName = userName; }
    public void setMailAddress(String mailAddress) { this.mailAddress = mailAddress; }
    public void setUrlPicture(String urlPicture) { this.urlPicture = urlPicture; }
}
