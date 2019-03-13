package com.openclassrooms.realestatemanager.views;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.controllers.activities.BaseActivity;
import com.openclassrooms.realestatemanager.controllers.fragments.BaseFragment;
import com.openclassrooms.realestatemanager.models.local.immovables.Picture;
import com.openclassrooms.realestatemanager.utils.LocalStorageHelper;


import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_detail_recycler_image_view)
    ImageView photoImageView;
    @BindView(R.id.fragment_detail_recycler_text_view)
    TextView photoTextView;

    public PhotoViewHolder(View itemView, ViewGroup parent) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        int height = parent.getWidth();
        int height2 = parent.getHeight();
        Log.e("AAAAAAAAAAAAAAAAAAAAAAA", "width = " + String.valueOf(height));
        Log.e("AAAAAAAAAAAAAAAAAAAAAAA", "width 2 = " + String.valueOf(height2));
    }

    public void updateWithPicture(Picture pic, Context context, int source){
        // this.photoImageView
        this.photoTextView.setText(pic.getPlace());

        // Use glide to resize the pic
        if(LocalStorageHelper.createOrGetFile(context.getFilesDir(), pic.getFileName()).exists()){
            Glide.with(context).load(LocalStorageHelper.createOrGetFile(context.getFilesDir(), pic.getFileName())).into(photoImageView);
            photoImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }else{
            try{
                Glide.with(context).load(pic.getUri()).into(photoImageView);
                photoImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}
