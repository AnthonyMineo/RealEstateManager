package com.openclassrooms.realestatemanager.utils.api;

import com.openclassrooms.realestatemanager.BuildConfig;
import com.openclassrooms.realestatemanager.models.remote.AlphaVantageResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AlphaVantageService {

    String apiKey = BuildConfig.AlphaVantage_ApiKey;

    @GET("query?function=CURRENCY_EXCHANGE_RATE&apikey="+ apiKey)
    Call<AlphaVantageResponse> getExchangeCurrencyRate(@Query("from_currency") String fromCurrency, @Query("to_currency") String toCurrency);
}
