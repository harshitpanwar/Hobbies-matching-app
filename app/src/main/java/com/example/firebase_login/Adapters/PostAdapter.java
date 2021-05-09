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
import com.example.firebase_login.Models.Post;
import com.example.firebase_login.Models.User;
import com.example.firebase_login.R;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {


    ArrayList<Post> list;
    Context context;


    public PostAdapter(ArrayList<Post> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_item_view,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        final Post post = list.get(position);
        final String Image_url = post.getPostimage();
        Glide.with(this.context).load(Image_url).into(holder.imageView);
        holder.userName.setText(post.getUserName());
        holder.description.setText(post.getDescription());
        Glide.with(this.context).load(post.getUserImageUrl()).into(holder.userImage);

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(context, usersChatActivity.class);
//                intent.putExtra("mName",po);
//                intent.putExtra("mProfile",Image_url);
//                intent.putExtra("mUid",user.getUid());
//                context.startActivity(intent);
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView,userImage;
        TextView userName, description;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.post_image);
            userName = itemView.findViewById(R.id.publisher);
            description = itemView.findViewById(R.id.description);
            userImage = itemView.findViewById(R.id.userImage);


        }
    }

}
