package com.openclassrooms.realestatemanager.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.local.Agent;

import java.util.ArrayList;
import java.util.List;

public class AgentAdapter extends RecyclerView.Adapter<AgentViewHolder> {

    private Context context;
    private List<Agent> agents;

    public AgentAdapter(){
        this.agents = new ArrayList<>();
    }

    @Override
    public AgentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.agent_list_recycle_item, parent, false);
        return new AgentViewHolder(view);
    }

    // UPDATE VIEW HOLDER WITH AGENTS
    @Override
    public void onBindViewHolder(AgentViewHolder viewHolder, int position) {
        viewHolder.updateWithAgent(this.agents.get(position), this.context);
    }

    // RETURN THE TOTAL COUNT OF ITEMS IN THE LIST
    @Override
    public int getItemCount() {
        return this.agents.size();
    }

    public Agent getAgent(int position){
        return this.agents.get(position);
    }

    public void updateData(List<Agent> agents){
        this.agents = agents;
        this.notifyDataSetChanged();
    }
}
