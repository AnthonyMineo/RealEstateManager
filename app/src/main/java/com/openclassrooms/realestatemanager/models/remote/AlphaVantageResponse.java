package com.openclassrooms.realestatemanager.models.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlphaVantageResponse {

    @SerializedName("Realtime Currency Exchange Rate")
    @Expose
    private CurrencyExchangeRate currencyExchangeRate;

    // --- GETTER ---
    public CurrencyExchangeRate getCurrencyExchangeRate() { return currencyExchangeRate; }

    // --- SETTER ---
    public void setCurrencyExchangeRate(CurrencyExchangeRate currencyExchangeRate) { this.currencyExchangeRate = currencyExchangeRate; }

}
