package com.ongo.dynamicformlibrary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ongo.dynamicformlibrary.utils.FormUtils;

import java.util.ArrayList;

/**
 * Created by gufran khan on 06-03-2019.
 */

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.MyViewHolder> {

    private ArrayList<String> photos;
    private Context mContext;

    public PhotosAdapter(ArrayList<String> photos, Context mContext) {
        this.photos = photos;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_photo, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        FormUtils.loadImage(myViewHolder.imageView, photos.get(i), mContext);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
