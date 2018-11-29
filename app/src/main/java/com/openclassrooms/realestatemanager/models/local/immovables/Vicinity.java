package com.openclassrooms.realestatemanager.models.local.immovables;

import android.support.annotation.Nullable;

public class Vicinity {

    private String address;
    @Nullable
    private String cptAddress;
    private String city;
    private String postalCode;
    private String country;

    // --- DEFAULT CONSTRUCTOR ---
    public Vicinity(){}

    // --- COMPLETE CONSTRUCTOR ---
    public Vicinity(String address, @Nullable String cptAddress, String city, String postalCode, String country){
        this.address = address;
        this.cptAddress = cptAddress;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    // --- GETTER ---
    public String getAddress() { return address; }
    @Nullable
    public String getCptAddress() { return cptAddress; }
    public String getCity() { return city; }
    public String getPostalCode() { return postalCode; }
    public String getCountry() { return country; }

    // --- SETTER ---
    public void setAddress(String address) { this.address = address; }
    public void setCptAddress(@Nullable String cptAddress) { this.cptAddress = cptAddress; }
    public void setCity(String city) { this.city = city; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public void setCountry(String country) { this.country = country; }

}
