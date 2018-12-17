package com.openclassrooms.realestatemanager.controllers.activities;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.local.Agent;
import com.openclassrooms.realestatemanager.utils.ItemClickSupport;
import com.openclassrooms.realestatemanager.viewmodels.ImmoViewModel;
import com.openclassrooms.realestatemanager.views.AgentAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.android.AndroidInjection;

public class LogginActivity extends BaseActivity {

    // DESIGN
    @BindView(R.id.loggin_list_recycler_view)
    RecyclerView recyclerView;

    // FOR DATA
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ImmoViewModel immoViewModel;
    private AgentAdapter agentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Init Dagger
        this.configureDagger();
        this.configureViewModel();
        this.configureRecyclerView();

    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_loggin;
    }

    private void configureDagger(){
        AndroidInjection.inject(this);
    }

    private void configureViewModel(){
        immoViewModel = ViewModelProviders.of(this, viewModelFactory).get(ImmoViewModel.class);
        immoViewModel.getAllAgent().observe(this, agents -> updateUI(agents));
    }

    // - Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView() {
        // - Create adapter passing the list of Restaurants
        this.agentAdapter = new AgentAdapter();
        // - Attach the adapter to the recyclerview to populate items
        this.recyclerView.setAdapter(this.agentAdapter);
        // - Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemClickSupport.addTo(recyclerView, R.layout.agent_list_recycle_item)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    immoViewModel.setCurrentUser(agentAdapter.getAgent(position));
                    Intent intent = new Intent(LogginActivity.this, MainActivity.class);
                    startActivity(intent);
                });
    }

    private void updateUI(List<Agent> agents){
        this.agentAdapter.updateData(agents);
    }
}
