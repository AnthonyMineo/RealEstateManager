package com.openclassrooms.realestatemanager.models.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.openclassrooms.realestatemanager.models.local.Agent;

import java.util.List;

@Dao
public interface AgentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAgent(Agent agent);

    @Query("SELECT * FROM Agent WHERE id = :agentId")
    LiveData<Agent> getAgentById(long agentId);

    @Query("SELECT * FROM Agent")
    LiveData<List<Agent>> getAllAgent();
}
