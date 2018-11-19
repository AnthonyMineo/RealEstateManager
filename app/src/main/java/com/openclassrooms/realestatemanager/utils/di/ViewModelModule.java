package com.openclassrooms.realestatemanager.utils.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.viewmodels.CurrencyExchangeRateViewModel;
import com.openclassrooms.realestatemanager.viewmodels.ImmoViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    // Provide a CER ViewModel for binding it
    @Binds
    @IntoMap
    @ViewModelKey(CurrencyExchangeRateViewModel.class)
    abstract ViewModel bindcerViewModel(CurrencyExchangeRateViewModel currencyExchangeRateViewModel);

    // Provide an Immo ViewModel for binding it
    @Binds
    @IntoMap
    @ViewModelKey(ImmoViewModel.class)
    abstract ViewModel bindImmoViewModel(ImmoViewModel immoViewModel);

    // Dagger 2 will provide our ViewModel Factory
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
