package com.openclassrooms.realestatemanager.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.local.immovables.Picture;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_detail_recycler_image_view)
    ImageView photoImageView;
    @BindView(R.id.fragment_detail_recycler_text_view)
    TextView photoTextView;

    public PhotoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithPicture(Picture pic, RequestManager glide){
       // this.photoImageView
        this.photoTextView.setText(pic.getPlace());
    }
}
