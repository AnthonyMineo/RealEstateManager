package com.openclassrooms.realestatemanager.models.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.openclassrooms.realestatemanager.models.remote.CurrencyExchangeRate;

@Dao
public interface CerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCER(CurrencyExchangeRate cer);

    @Query("SELECT * FROM CurrencyExchangeRate WHERE id = :cerId")
    LiveData<CurrencyExchangeRate> getCERById(int cerId);

    @Update
    int updateCER(CurrencyExchangeRate cer);

    @Query("SELECT * FROM CurrencyExchangeRate WHERE id = :cerId")
    CurrencyExchangeRate hasCER(int cerId);
}
