package com.openclassrooms.realestatemanager.models.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.openclassrooms.realestatemanager.models.Agent;
import com.openclassrooms.realestatemanager.models.Immo;
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

