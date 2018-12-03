package com.openclassrooms.realestatemanager.models.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.openclassrooms.realestatemanager.models.local.immovables.Immo;

import java.util.List;

@Dao
public interface ImmoDao {

    @Query("SELECT * FROM Immo WHERE agentId = :agentId")
    LiveData<List<Immo>> getImmosByAgent(int agentId);

    @Query("SELECT * FROM Immo")
    LiveData<List<Immo>> getAllImmos();

    @Query("SELECT * FROM Immo WHERE id = :immoId")
    LiveData<Immo> getImmoById(int immoId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertImmo(Immo immo);

    @Update
    int updateImmo(Immo immo);

    @Query("DELETE FROM Immo WHERE id = :immoId")
    int deleteImmo(int immoId);

    @Query("SELECT * FROM Immo WHERE id = :immoId")
    Immo hasImmo(int immoId);
}
