package com.openclassrooms.realestatemanager.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.local.immovables.Immo;
import com.openclassrooms.realestatemanager.utils.LocalStorageHelper;
import com.openclassrooms.realestatemanager.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImmoViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.immo_list_recycle_image) ImageView immoIcon;
    @BindView(R.id.immo_list_recycle_type) TextView immoType;
    @BindView(R.id.immo_list_recyle_city) TextView immoCity;
    @BindView(R.id.immo_list_recycle_price) TextView immoPrice;
    @BindView(R.id.recycler_sold_image_view) ImageView soldImage;

    public ImmoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithImmovables(Immo immo, Context context) {
        this.immoType.setText(immo.getType());
        this.immoCity.setText(immo.getVicinity().getCity());
        this.immoPrice.setText(Utils.formatPriceForImageView(immo.getPrice(), "$"));

        if(LocalStorageHelper.createOrGetFile(context.getFilesDir(), immo.getGallery().get(0).getFileName()).exists()){
            Glide.with(context).load(LocalStorageHelper.createOrGetFile(context.getFilesDir(), immo.getGallery().get(0).getFileName())).into(immoIcon);
            immoIcon.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        if(immo.getSellingDate() != -1){
            Glide.with(context).load(R.drawable.sold).into(soldImage);
        }
    }
}

