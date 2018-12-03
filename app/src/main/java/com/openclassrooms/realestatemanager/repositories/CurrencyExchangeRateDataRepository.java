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
    private final int CER_ID = 1;

    // --- CONSTRUCTOR ---
    @Inject
    public CurrencyExchangeRateDataRepository(CerDao cerDao, Executor executor, AlphaVantageService alphaVantageService){
        this.cerDao = cerDao;
        this.executor = executor;
        this.alphaVantageService = alphaVantageService;
    }

    // --- GETTER ---
    public LiveData<CurrencyExchangeRate> getCER(String fromCurrency, String toCurrency) {
        getCERFromAPI(fromCurrency, toCurrency);
        // Returns a LiveData object directly from the database.
        return cerDao.getCERById(CER_ID);
    }

    // --- CREATE ---
    public void createCER(CurrencyExchangeRate cer){ cerDao.insertCER(cer); }

    // --- UPDATE ---
    public void updateCER(CurrencyExchangeRate cer){ cerDao.updateCER(cer); }

    // --- REMOTE DATA GETTER ---
    private void getCERFromAPI(final String fromCurrency, final String toCurrency) {
        // Runs in a background thread.
        executor.execute(() -> {
            // Check if CER already exist in db and update it if he is not
            boolean cerExists = (cerDao.hasCER(CER_ID) != null);
            if (!cerExists) {
                alphaVantageService.getExchangeCurrencyRate(fromCurrency, toCurrency).enqueue(new Callback<AlphaVantageResponse>() {
                    @Override
                    public void onResponse(Call<AlphaVantageResponse> call, Response<AlphaVantageResponse> response) {
                        executor.execute(() -> {
                            if(response.isSuccessful()){
                                CurrencyExchangeRate cer = response.body().getCurrencyExchangeRate();
                                if(cer != null){
                                    // Always write CER with id = 1 because we need only one cer.
                                    cer.setId(CER_ID);
                                    cerDao.insertCER(cer);
                                }
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
