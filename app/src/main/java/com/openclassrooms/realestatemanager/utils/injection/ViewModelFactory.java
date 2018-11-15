package com.openclassrooms.realestatemanager.utils.injection;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.openclassrooms.realestatemanager.repositories.AgentDataRepository;
import com.openclassrooms.realestatemanager.repositories.ImmoDataRepository;
import com.openclassrooms.realestatemanager.viewmodels.CurrencyExchangeRateViewModel;
import com.openclassrooms.realestatemanager.viewmodels.ImmoViewModel;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final ImmoDataRepository immoDataSource;
    private final AgentDataRepository agentDataRepository;
    private final Executor executor;

    public ViewModelFactory(ImmoDataRepository immoDataSource, AgentDataRepository agentDataRepository, Executor executor){
        this.immoDataSource = immoDataSource;
        this.agentDataRepository = agentDataRepository;
        this.executor = executor;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ImmoViewModel.class)) {
            return (T) new ImmoViewModel(immoDataSource, agentDataRepository, executor);
        } else if( modelClass.isAssignableFrom(CurrencyExchangeRateViewModel.class)){
            // don't forget to put args when you will define them
            return (T) new CurrencyExchangeRateViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel classe");
    }
}
