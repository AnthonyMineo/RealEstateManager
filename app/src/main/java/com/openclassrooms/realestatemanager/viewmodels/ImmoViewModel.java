package com.openclassrooms.realestatemanager.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.models.local.Agent;
import com.openclassrooms.realestatemanager.models.local.immovables.Immo;
import com.openclassrooms.realestatemanager.repositories.AgentDataRepository;
import com.openclassrooms.realestatemanager.repositories.ImmoDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

public class ImmoViewModel extends ViewModel {

    // --- REPOSITORIES ---
    private ImmoDataRepository immoDataSource;
    private AgentDataRepository agentDateSource;
    private Executor executor;

    // --- DATA ---
    private LiveData<Agent> currentUser;

    // --- CONSTRUCTOR ---
    @Inject
    public ImmoViewModel(ImmoDataRepository immoDataSource, AgentDataRepository agentDateSource, Executor executor){
        this.immoDataSource = immoDataSource;
        this.agentDateSource = agentDateSource;
        this.executor = executor;
    }

    public void initCurrentUser(int agentId){
        if(this.currentUser != null){
            return;
        }
        currentUser = agentDateSource.getAgentById(agentId);
    }

    // --- FOR AGENT ---
    public LiveData<Agent> getCurrentUser() { return this.currentUser; }

    // --- FOR IMMO ---
    public LiveData<List<Immo>> getImmosByAgent(int agentId) { return immoDataSource.getImmosByAgent(agentId); }
    public LiveData<List<Immo>> getAllImmos() { return immoDataSource.getAllImmos(); }
    public void createImmo(Immo immo) { executor.execute(() -> immoDataSource.createImmo(immo)); }
    public void updateImmo(Immo immo) { executor.execute(() -> immoDataSource.updateImmo(immo)); }
    public void deleteimmo(int immoId) { executor.execute(() -> immoDataSource.deleteImmo(immoId));}
}
