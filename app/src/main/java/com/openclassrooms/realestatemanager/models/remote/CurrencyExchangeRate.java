package com.openclassrooms.realestatemanager.models.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrencyExchangeRate {

    @SerializedName("5. Exchange Rate")
    @Expose
    private String ExchangeRate;
    @SerializedName("6. Last Refreshed")
    @Expose
    private String LastRefreshed;
    @SerializedName("7. Time Zone")
    @Expose
    private String TimeZone;

    // --- GETTER ---
    public String getExchangeRate() { return ExchangeRate; }
    public String getLastRefreshed() { return LastRefreshed; }
    public String getTimeZone() { return TimeZone; }

    // --- SETTER ---
    public void setExchangeRate(String exchangeRate) { ExchangeRate = exchangeRate; }
    public void setLastRefreshed(String lastRefreshed) { LastRefreshed = lastRefreshed; }
    public void setTimeZone(String timeZone) { TimeZone = timeZone; }
}
