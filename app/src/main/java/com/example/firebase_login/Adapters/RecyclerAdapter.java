package com.example.firebase_login.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firebase_login.R;
import com.example.firebase_login.Activities.User_Info;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{

    ArrayList<String> user_names_list;
    ArrayList<String> user_emails_list;
    ArrayList<String> user_images;
    ArrayList<String> user_id_list;
    Context context;

    public RecyclerAdapter(ArrayList<String> user_names_list, ArrayList<String> user_emails_list, ArrayList<String> user_id_list, ArrayList<String> user_images, Context context){

        this.user_names_list = user_names_list;
        this.user_emails_list = user_emails_list;
        this.user_id_list = user_id_list;
        this.user_images = user_images;
        this.context = context;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v =  layoutInflater.inflate(R.layout.user_row_item, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.userName.setText(user_names_list.get(position));
        Glide.with(this.context)
                .load(user_images
                .get(position))
                .into(holder.userImage);

    }

    @Override
    public int getItemCount() {
        return user_names_list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CircleImageView userImage;
        TextView userName;


            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                userImage = itemView.findViewById(R.id.user_image_view);
                userName = itemView.findViewById(R.id.user_name);
                itemView.setOnClickListener(this);


            }

        @Override
        public void onClick(View view) {

            Intent intent = new Intent(view.getContext(), User_Info.class);
            intent.putExtra("username", user_names_list.get(getAdapterPosition()));
            intent.putExtra("useremail",user_emails_list.get(getAdapterPosition()));
            intent.putExtra("userid", user_id_list.get(getAdapterPosition()));
            intent.putExtra("userImageLink", user_images.get(getAdapterPosition()));
            view.getContext().startActivity(intent);


        }
    }

}