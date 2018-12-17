package com.openclassrooms.realestatemanager.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.local.Agent;


import butterknife.BindView;
import butterknife.ButterKnife;

public class AgentViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.agent_list_recycle_image)
    ImageView agentIcon;
    @BindView(R.id.agent_list_recycle_name)
    TextView agentName;

    public AgentViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithAgent(Agent agent, Context context) {
        this.agentName.setText(agent.getUserName());
        try {
            Glide.with(context).load(agent.getUrlPicture()).into(agentIcon);
        } catch (Exception e){

        }
    }
}
