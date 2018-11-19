package com.openclassrooms.realestatemanager.models.remote;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@Entity
public class CurrencyExchangeRate {

    @PrimaryKey
    private int id;
    @SerializedName("5. Exchange Rate")
    @Expose
    private String exchangeRate;
    @SerializedName("6. Last Refreshed")
    @Expose
    private String lastRefreshed;
    @SerializedName("7. Time Zone")
    @Expose
    private String timeZone;

    // --- CONSTRUCTOR ---
    public CurrencyExchangeRate(int id, String exchangeRate, String lastRefreshed, String timeZone){
        this.id = id;
        this.exchangeRate = exchangeRate;
        this.lastRefreshed = lastRefreshed;
        this.timeZone = timeZone;
    }

    // --- GETTER ---
    public int getId() { return id; }
    public String getExchangeRate() { return exchangeRate; }
    public String getLastRefreshed() { return lastRefreshed; }
    public String getTimeZone() { return timeZone; }

    // --- SETTER ---
    public void setId(int id) { this.id = id; }
    public void setExchangeRate(String exchangeRate) { this.exchangeRate = exchangeRate; }
    public void setLastRefreshed(String lastRefreshed) { this.lastRefreshed = lastRefreshed; }
    public void setTimeZone(String timeZone) { this.timeZone = timeZone; }
}
