package com.openclassrooms.realestatemanager.views;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.local.immovables.Picture;
import com.openclassrooms.realestatemanager.utils.LocalStorageHelper;

import java.io.File;

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

    public void updateWithPicture(Picture pic, Context context, int source){
        // this.photoImageView
        this.photoTextView.setText(pic.getPlace());
        // Use glide to resize the pic
        if(source == 0){
            // need a test when image fully write
            if(LocalStorageHelper.createOrGetFile(context.getFilesDir(), pic.getFileName()).exists())
                Glide.with(context).load(LocalStorageHelper.createOrGetFile(context.getFilesDir(), pic.getFileName())).into(photoImageView);
        } else if (source == 1){
            try{
                Glide.with(context).load(pic.getUri()).into(photoImageView);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
