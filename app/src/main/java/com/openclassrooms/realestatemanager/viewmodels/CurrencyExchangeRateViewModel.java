package com.openclassrooms.realestatemanager.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.openclassrooms.realestatemanager.models.remote.CurrencyExchangeRate;
import com.openclassrooms.realestatemanager.repositories.CurrencyExchangeRateDataRepository;

import java.util.concurrent.Executor;

import javax.inject.Inject;


public class CurrencyExchangeRateViewModel extends ViewModel{

    // --- REPOSITORY ---
    private CurrencyExchangeRateDataRepository cerDataSource;
    private Executor executor;

    // --- DATA ---
    private LiveData<CurrencyExchangeRate> cer;

    // --- CONSTRUCTOR ---
    @Inject
    public CurrencyExchangeRateViewModel(CurrencyExchangeRateDataRepository cerDataSource, Executor executor){
        this.cerDataSource = cerDataSource;
        this.executor = executor;
    }

    public void initCER(String fromCurrency, String toCurrency) {
        if (this.cer != null) {
            return;
        }
        cer = cerDataSource.getCER(fromCurrency, toCurrency);
        Log.e("TAG", "CER IS INIT");
    }

    // --- FOR CER ---
    public LiveData<CurrencyExchangeRate> getCER(){
        Log.e("TAG", "GETTING CER");
        return this.cer; }

    public void createCER(CurrencyExchangeRate cer) { executor.execute(() -> cerDataSource.createCER(cer)); }
    public void updateCER(CurrencyExchangeRate cer) { executor.execute(() -> cerDataSource.updateCER(cer)); }

}
