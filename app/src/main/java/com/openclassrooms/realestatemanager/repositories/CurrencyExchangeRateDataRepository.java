package com.openclassrooms.realestatemanager.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.openclassrooms.realestatemanager.models.remote.AlphaVantageResponse;
import com.openclassrooms.realestatemanager.utils.api.AlphaVantageService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrencyExchangeRateDataRepository {

    private AlphaVantageService apiService;
    private static final CurrencyExchangeRateDataRepository INSTANCE = new CurrencyExchangeRateDataRepository();

    public static CurrencyExchangeRateDataRepository getInstance()
    {
        return INSTANCE;
    }

    public CurrencyExchangeRateDataRepository()
    {
        apiService = apiService.retrofit.create((AlphaVantageService.class));
    }

    public LiveData<AlphaVantageResponse> getExchangeCurrencyRate(String fromCurrency, String toCurrency)
    {
        final MutableLiveData<AlphaVantageResponse> data = new MutableLiveData<>();
        apiService.getExchangeCurrencyRate(fromCurrency, toCurrency)
                .enqueue(new Callback<AlphaVantageResponse>()
                {
                    @Override
                    public void onResponse(Call<AlphaVantageResponse> call, Response<AlphaVantageResponse> response)
                    {
                        if (response.isSuccessful())
                            data.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<AlphaVantageResponse> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }
}
