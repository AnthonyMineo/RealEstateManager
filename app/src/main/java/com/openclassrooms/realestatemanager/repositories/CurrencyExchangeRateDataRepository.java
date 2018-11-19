package com.openclassrooms.realestatemanager.repositories;

import android.arch.lifecycle.LiveData;

import android.util.Log;

import com.openclassrooms.realestatemanager.models.database.dao.CerDao;
import com.openclassrooms.realestatemanager.models.remote.AlphaVantageResponse;
import com.openclassrooms.realestatemanager.models.remote.CurrencyExchangeRate;
import com.openclassrooms.realestatemanager.utils.api.AlphaVantageService;

import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrencyExchangeRateDataRepository {

    private final CerDao cerDao;
    private final Executor executor;
    private AlphaVantageService alphaVantageService;

    // --- CONSTRUCTOR ---
    @Inject
    public CurrencyExchangeRateDataRepository(CerDao cerDao, Executor executor, AlphaVantageService alphaVantageService){
        this.cerDao = cerDao;
        this.executor = executor;
        this.alphaVantageService = alphaVantageService;
    }

    // --- GETTER ---
    public LiveData<CurrencyExchangeRate> getCER(String fromCurrency, String toCurrency) {
        refreshCER(fromCurrency, toCurrency);
        Log.e("TAG", "GETTING CER FROM DAO");
        // Returns a LiveData object directly from the database.
        return cerDao.getCERById(1);
    }

    // --- CREATE ---
    public void createCER(CurrencyExchangeRate cer){ cerDao.insertCER(cer); }

    // --- UPDATE ---
    public void updateCER(CurrencyExchangeRate cer){ cerDao.updateCER(cer); }

    private void refreshCER(final String fromCurrency, final String toCurrency) {
        // Runs in a background thread.
        executor.execute(() -> {
            // Check if CER data was fetched recently.
            boolean cerExists = (cerDao.hasCER(1) != null);
            // If CER have to be update
            if (!cerExists) {
                alphaVantageService.getExchangeCurrencyRate(fromCurrency, toCurrency).enqueue(new Callback<AlphaVantageResponse>() {
                    @Override
                    public void onResponse(Call<AlphaVantageResponse> call, Response<AlphaVantageResponse> response) {
                        Log.e("TAG", "Data refreshed from network !");
                        executor.execute(() -> {
                            if(response.isSuccessful()){
                                Log.e("TAG", "SUCCESS");
                                AlphaVantageResponse avr = response.body();
                                CurrencyExchangeRate cer = avr.getCurrencyExchangeRate();
                                if(cer != null){
                                    Log.e("TAG", cer.getExchangeRate());
                                    cer.setId(1);
                                    cerDao.insertCER(cer);
                                }
                            } else {
                                Log.e("TAG", "FAILED");
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<AlphaVantageResponse> call, Throwable t) {
                        Log.e("TAG", "FAILED from network !");
                    }
                });
            }
        });
    }
}
