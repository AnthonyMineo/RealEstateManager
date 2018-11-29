package com.openclassrooms.realestatemanager.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.local.immovables.Immo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImmoViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.immo_list_recycle_image) ImageView immoIcon;
    @BindView(R.id.immo_list_recycle_type) TextView immoType;
    @BindView(R.id.immo_list_recyle_city) TextView immoCity;
    @BindView(R.id.immo_list_recycle_price) TextView immoPrice;

    public ImmoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithImmovables(Immo immo, RequestManager glide){
        this.immoType.setText(immo.getType());
        this.immoCity.setText(immo.getVicinity().getCity());
        this.immoPrice.setText(String.valueOf(immo.getPrice()));
    /*
        try{
            immoIcon.setImageURI();
        } catch (Exception e){
            e.printStackTrace();
        }
    */
    }
}

