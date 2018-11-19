package com.openclassrooms.realestatemanager.repositories;

import android.arch.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.models.Agent;
import com.openclassrooms.realestatemanager.models.database.dao.AgentDao;

import javax.inject.Inject;
import javax.inject.Singleton;

public class AgentDataRepository {

    private final AgentDao agentDao;

    // --- CONSTRUCTOR ---
    @Inject
    public AgentDataRepository(AgentDao agentDao){
        this.agentDao = agentDao;
    }

    // --- GET ---
    public LiveData<Agent> getAgentById(int agentId) { return this.agentDao.getAgentById(agentId); }

    // --- CREATE ---
    public void createAgent(Agent agent) { agentDao.insertAgent(agent); }
}
