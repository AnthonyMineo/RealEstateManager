package com.openclassrooms.realestatemanager.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.local.immovables.Picture;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoViewHolder> {

    private List<Picture> gallery;
    private Context context;
    private int source;

    public PhotoAdapter(int source){
        this.gallery = new ArrayList<>();
        this.source = source;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.photo_list_recycle_item, parent, false);
        return new PhotoViewHolder(view, parent);
    }

    // UPDATE VIEW HOLDER WITH PICTURE
    @Override
    public void onBindViewHolder(PhotoViewHolder viewHolder, int position) {
        viewHolder.updateWithPicture(this.gallery.get(position), this.context, this.source);
    }

    // RETURN THE TOTAL COUNT OF ITEMS IN THE LIST
    @Override
    public int getItemCount() {
        return this.gallery.size();
    }

    public Picture getPhoto(int position){
        return this.gallery.get(position);
    }
    public void deletePhoto(int position) {
        this.gallery.remove(position);
        this.notifyDataSetChanged();
    }

    public void updateData(List<Picture> gallery){
        this.gallery = gallery;
        this.notifyDataSetChanged();
    }

    public void addData(Picture pic){
        this.gallery.add(pic);
        this.notifyDataSetChanged();
    }
}
