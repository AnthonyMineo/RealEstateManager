package com.openclassrooms.realestatemanager.repositories;

import android.arch.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.models.local.Agent;
import com.openclassrooms.realestatemanager.models.database.dao.AgentDao;

import java.util.List;

import javax.inject.Inject;

public class AgentDataRepository {

    private final AgentDao agentDao;
    private Agent currentUser = new Agent();

    // --- CONSTRUCTOR ---
    @Inject
    public AgentDataRepository(AgentDao agentDao){
        this.agentDao = agentDao;
    }

    // --- GET ---
    public LiveData<Agent> getAgentById(long agentId) { return this.agentDao.getAgentById(agentId); }
    public LiveData<List<Agent>> getAllAgent() { return this.agentDao.getAllAgent(); }

    // --- CREATE ---
    public void createAgent(Agent agent) { agentDao.insertAgent(agent); }

    public Agent getCurrentUser() { return currentUser; }
    public void setCurrentUser(Agent currentUser) { this.currentUser = currentUser; }
}
