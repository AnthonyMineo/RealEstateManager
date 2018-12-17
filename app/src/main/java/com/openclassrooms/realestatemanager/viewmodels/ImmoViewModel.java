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
    private AgentDataRepository agentDataSource;
    private Executor executor;

    // --- CONSTRUCTOR ---
    @Inject
    public ImmoViewModel(ImmoDataRepository immoDataSource, AgentDataRepository agentDateSource, Executor executor){
        this.immoDataSource = immoDataSource;
        this.agentDataSource = agentDateSource;
        this.executor = executor;
    }


    // --- FOR AGENT ---
    public LiveData<Agent> getUserById(long agentId){ return agentDataSource.getAgentById(agentId); }
    public LiveData<List<Agent>> getAllAgent(){ return agentDataSource.getAllAgent(); }

    public Agent getCurrentUser() { return agentDataSource.getCurrentUser(); }
    public void setCurrentUser(Agent user) { agentDataSource.setCurrentUser(user); }

    // --- FOR IMMO ---
    public LiveData<List<Immo>> getImmosByAgent(long agentId) { return immoDataSource.getImmosByAgent(agentId); }
    public LiveData<List<Immo>> getAllImmos() { return immoDataSource.getAllImmos(); }
    public LiveData<List<Immo>> getSearchImmos(int minPrice, int maxPrice, int minSurface, int maxSurface, int enterDate, int sellingDate) {
        return immoDataSource.getSearchImmos(minPrice, maxPrice, minSurface, maxSurface, enterDate, sellingDate); }
    public LiveData<Immo> getImmoById(long immoId) { return immoDataSource.getImmoById(immoId); }
    public void createImmo(Immo immo) { executor.execute(() -> immoDataSource.createImmo(immo)); }
    public void updateImmo(Immo immo) { executor.execute(() -> immoDataSource.updateImmo(immo)); }
    public void deleteimmo(long immoId) { executor.execute(() -> immoDataSource.deleteImmo(immoId));}

    public LiveData<Immo> getSelectedImmo() {
        return immoDataSource.getSelectedImmo();
    }
    public Immo getTempImmo() { return immoDataSource.getTempImmo(); }

    public void setSelectedImmo(Immo selectedImmo) {
        immoDataSource.setSelectedImmo(selectedImmo);
    }
    public void setTempImmo(Immo tempImmo) {
        immoDataSource.setTempImmo(tempImmo);
    }

}
