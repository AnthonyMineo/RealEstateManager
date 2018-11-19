package com.openclassrooms.realestatemanager.models.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlphaVantageResponse {

    @SerializedName("Realtime Currency Exchange Rate")
    @Expose
    private CurrencyExchangeRate currencyExchangeRate;
    @SerializedName("5. Exchange Rate")
    @Expose
    private String exchangeRate;

    // --- GETTER ---
    public CurrencyExchangeRate getCurrencyExchangeRate() { return currencyExchangeRate; }

    // --- SETTER ---
    public void setCurrencyExchangeRate(CurrencyExchangeRate currencyExchangeRate) { this.currencyExchangeRate = currencyExchangeRate; }
}
