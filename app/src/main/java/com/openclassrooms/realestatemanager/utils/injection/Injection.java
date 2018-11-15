package com.openclassrooms.realestatemanager.utils.injection;

import android.content.Context;

import com.openclassrooms.realestatemanager.models.database.RealEstateDB;
import com.openclassrooms.realestatemanager.repositories.AgentDataRepository;
import com.openclassrooms.realestatemanager.repositories.ImmoDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static ImmoDataRepository immoDataSourceProvider(Context context){
        RealEstateDB db = RealEstateDB.getInstance(context);
        return new ImmoDataRepository(db.immoDao());
    }

    public static AgentDataRepository sAgentDataSourceProvider(Context context){
        RealEstateDB db = RealEstateDB.getInstance(context);
        return new AgentDataRepository(db.agentDao());
    }

    public static Executor executorProvider() { return Executors.newSingleThreadExecutor(); }

    public static ViewModelFactory viewModelFactoryProvider(Context context){
        ImmoDataRepository immoDataSource = immoDataSourceProvider(context);
        AgentDataRepository agentDataSource = sAgentDataSourceProvider(context);
        Executor executor = executorProvider();
        return new ViewModelFactory(immoDataSource, agentDataSource, executor);
    }
}
