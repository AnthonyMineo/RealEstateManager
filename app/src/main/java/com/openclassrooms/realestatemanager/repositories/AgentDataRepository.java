package com.openclassrooms.realestatemanager.repositories;

import android.arch.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.models.local.Agent;
import com.openclassrooms.realestatemanager.models.database.dao.AgentDao;

import javax.inject.Inject;

public class AgentDataRepository {

    private final AgentDao agentDao;

    // --- CONSTRUCTOR ---
    @Inject
    public AgentDataRepository(AgentDao agentDao){
        this.agentDao = agentDao;
    }

    // --- GET ---
    public LiveData<Agent> getAgentById(long agentId) { return this.agentDao.getAgentById(agentId); }

    // --- CREATE ---
    public void createAgent(Agent agent) { agentDao.insertAgent(agent); }
}
