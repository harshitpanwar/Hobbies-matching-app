package com.example.firebase_login.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firebase_login.Activities.usersChatActivity;
import com.example.firebase_login.Models.User;
import com.example.firebase_login.Models.UserPhotosModel;
import com.example.firebase_login.R;

import java.util.ArrayList;

public class User_photos extends RecyclerView.Adapter<User_photos.ViewHolder> {


    ArrayList<UserPhotosModel> list;
    Context context;


    public User_photos(ArrayList<UserPhotosModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_profile_photos,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        final UserPhotosModel userPhoto = list.get(position);
        final String Image_url = userPhoto.getImageUrl();
        Glide.with(this.context).load(Image_url).into(holder.imageView);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.profile_section_image);



        }
    }

}
