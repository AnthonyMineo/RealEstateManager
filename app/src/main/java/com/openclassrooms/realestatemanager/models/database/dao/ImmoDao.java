package com.openclassrooms.realestatemanager.models.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.openclassrooms.realestatemanager.models.local.immovables.Immo;

import java.util.List;

@Dao
public interface ImmoDao {

    @Query("SELECT * FROM Immo WHERE agentId = :agentId")
    LiveData<List<Immo>> getImmosByAgent(long agentId);

    @Query("SELECT * FROM Immo")
    LiveData<List<Immo>> getAllImmos();

    @Query("SELECT * FROM Immo WHERE id = :immoId")
    LiveData<Immo> getImmoById(long immoId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertImmo(Immo immo);

    @Update
    int updateImmo(Immo immo);

    @Query("DELETE FROM Immo WHERE id = :immoId")
    int deleteImmo(long immoId);

    @Query("SELECT * FROM Immo WHERE id = :immoId")
    Immo hasImmo(long immoId);


    // FOR CONTENT PROVIDER
    @Query("SELECT * FROM Immo ")
    Cursor getAllImmoWithCursor();


    // Search QUERIES
    // All init
    @Query("SELECT * FROM Immo WHERE price >= :minPrice AND price <= :maxPrice AND surface >= :minSurface " +
            "AND surface <= :maxSurface AND enterDate >= :enterDate AND sellingDate >= :sellingDate")
    LiveData<List<Immo>> getSearchImmos(int minPrice, int maxPrice, int minSurface, int maxSurface, int enterDate, int sellingDate);

    // no maxPrice
    @Query("SELECT * FROM Immo WHERE price >= :minPrice AND surface >= :minSurface " +
            "AND surface <= :maxSurface AND enterDate >= :enterDate AND sellingDate >= :sellingDate")
    LiveData<List<Immo>> getSearchImmos1(int minPrice, int minSurface, int maxSurface, int enterDate, int sellingDate);

    // no maxSurface
    @Query("SELECT * FROM Immo WHERE price >= :minPrice AND price <= :maxPrice AND surface >= :minSurface " +
            "AND enterDate >= :enterDate AND sellingDate >= :sellingDate")
    LiveData<List<Immo>> getSearchImmos2(int minPrice, int maxPrice, int minSurface, int enterDate, int sellingDate);

    // no both
    @Query("SELECT * FROM Immo WHERE price >= :minPrice AND surface >= :minSurface " +
            "AND enterDate >= :enterDate AND sellingDate >= :sellingDate")
    LiveData<List<Immo>> getSearchImmo3(int minPrice, int minSurface, int enterDate, int sellingDate);

}
