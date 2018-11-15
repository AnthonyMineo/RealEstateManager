package com.openclassrooms.realestatemanager.repositories;

import android.arch.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.models.Immo;
import com.openclassrooms.realestatemanager.models.database.dao.ImmoDao;

import java.util.List;

public class ImmoDataRepository {

    private final ImmoDao immoDao;

    public ImmoDataRepository(ImmoDao immoDao) { this.immoDao = immoDao; }

    // --- GET ---
    public LiveData<List<Immo>> getImmosByAgent(int agentId){ return this.immoDao.getImmosByAgent(agentId); }
    public LiveData<List<Immo>> getAllImmos(){ return this.immoDao.getAllImmos(); }

    // --- CREATE ---
    public void createImmo(Immo immo){ immoDao.insertImmo(immo); }

    // --- DELETE ---
    public void deleteImmo(int immoId){ immoDao.deleteImmo(immoId); }

    // --- UPDATE ---
    public void updateImmo(Immo immo){ immoDao.updateImmo(immo); }
}
