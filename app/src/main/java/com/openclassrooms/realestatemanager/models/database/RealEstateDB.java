package com.openclassrooms.realestatemanager.models.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.openclassrooms.realestatemanager.models.local.Agent;
import com.openclassrooms.realestatemanager.models.local.immovables.Immo;
import com.openclassrooms.realestatemanager.models.database.dao.AgentDao;
import com.openclassrooms.realestatemanager.models.database.dao.CerDao;
import com.openclassrooms.realestatemanager.models.database.dao.ImmoDao;
import com.openclassrooms.realestatemanager.models.remote.CurrencyExchangeRate;

@Database(entities = {Immo.class, Agent.class, CurrencyExchangeRate.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class RealEstateDB extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile RealEstateDB INSTANCE;

    // --- DAO
    public abstract ImmoDao immoDao();
    public abstract AgentDao agentDao();
    public abstract CerDao cerDao();
}

