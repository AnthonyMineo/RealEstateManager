package com.openclassrooms.realestatemanager.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;

import com.openclassrooms.realestatemanager.models.local.Agent;
import com.openclassrooms.realestatemanager.models.local.immovables.Immo;
import com.openclassrooms.realestatemanager.repositories.AgentDataRepository;
import com.openclassrooms.realestatemanager.repositories.ImmoDataRepository;
import com.openclassrooms.realestatemanager.utils.LocalStorageHelper;

import java.io.File;
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

    public void initCurrentUser(long agentId){
        if(this.currentUser != null){
            return;
        }
        currentUser = agentDateSource.getAgentById(agentId);
    }

    // --- FOR AGENT ---
    public LiveData<Agent> getCurrentUser() { return this.currentUser; }

    // --- FOR IMMO ---
    public LiveData<List<Immo>> getImmosByAgent(long agentId) { return immoDataSource.getImmosByAgent(agentId); }
    public LiveData<List<Immo>> getAllImmos() { return immoDataSource.getAllImmos(); }
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
