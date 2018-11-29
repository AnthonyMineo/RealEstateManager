package com.openclassrooms.realestatemanager.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.local.immovables.Immo;

import java.util.ArrayList;
import java.util.List;

public class ImmoAdapter extends RecyclerView.Adapter<ImmoViewHolder> {

    private RequestManager glide;
    private List<Immo> immos;

    public ImmoAdapter(RequestManager glide){
        this.immos = new ArrayList<>();
        this.glide = glide;
    }

    @Override
    public ImmoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.immo_list_recycle_item, parent, false);
        return new ImmoViewHolder(view);
    }

    // UPDATE VIEW HOLDER WITH IMMOVABLES
    @Override
    public void onBindViewHolder(ImmoViewHolder viewHolder, int position) {
        viewHolder.updateWithImmovables(this.immos.get(position), this.glide);
    }

    // RETURN THE TOTAL COUNT OF ITEMS IN THE LIST
    @Override
    public int getItemCount() {
        return this.immos.size();
    }

    public Immo getImmo(int position){
        return this.immos.get(position);
    }

    public void updateData(List<Immo> immos){
        this.immos = immos;
        this.notifyDataSetChanged();
    }
}
