package com.example.firebase_login.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.firebase_login.Activities.usersChatActivity;
import com.example.firebase_login.Models.Post;
import com.example.firebase_login.Models.User;
import com.example.firebase_login.R;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;
import com.google.android.gms.common.internal.Objects;
import com.pedromassango.doubleclick.DoubleClick;
import com.pedromassango.doubleclick.DoubleClickListener;

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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final Post post = list.get(position);

        //Initialize Shimmer

//        Shimmer shimmer = new Shimmer.ColorHighlightBuilder()
//                .setBaseColor(Color.parseColor("F3F3F3"))
//                .setBaseAlpha(1)
//                .setHighlightColor(Color.parseColor("E7E7E7"))
//                .setHighlightAlpha(1)
//                .setDropoff(50)
//                .build();
//
//        //Initialize shimmer drawable
//        ShimmerDrawable shimmerDrawable = new ShimmerDrawable();
//        //set shimmer
//        shimmerDrawable.setShimmer(shimmer);


        // Set Data



        holder.imageView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.simple_fadeout_animation));
        holder.description.setAnimation(AnimationUtils.loadAnimation(context, R.anim.simple_fadeout_animation));
        holder.userImage.setAnimation(AnimationUtils.loadAnimation(context, R.anim.simple_fadeout_animation));
        final SharedPreferences sharedPreferences = context.getSharedPreferences("userdetails", Context.MODE_PRIVATE);

        final String Image_url = post.getPostimage();
        Glide.with(this.context).load(Image_url).into(holder.imageView);
        holder.userName.setText(post.getUserName());
        holder.description.setText(post.getDescription());
        Glide.with(this.context)
                .load(post.getUserImageUrl())
                .placeholder(R.drawable.background)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable  GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }



                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                        holder.progressBar.setVisibility(View.GONE);

                        return false;
                    }
                })
                .into(holder.userImage);

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

        holder.like.setTag("heart");
        //on click listener for like button
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(String.valueOf(holder.like.getTag())=="heart"){

                    holder.like.setTag("heart1");
                    holder.like.setImageResource(R.drawable.ic_baseline_thumb_up_24);
                    String likes = holder.Nlikes.getText().toString();
                    int likess = Integer.parseInt(likes)+1;
                    holder.Nlikes.setText(likess+"");

                }
                else if(String.valueOf(holder.like.getTag())=="heart1"){
                    holder.like.setTag("heart");
                    holder.like.setImageResource(R.drawable.ic_outline_thumb_up_24);
                    String likes = holder.Nlikes.getText().toString();
                    int likess = Integer.parseInt(likes)-1;
                    holder.Nlikes.setText(likess+"");
                }


            }
        });

        holder.imageView.setOnClickListener(new DoubleClick(new DoubleClickListener() {
            @Override
            public void onSingleClick(View view) {

            }

            @Override
            public void onDoubleClick(View view) {


                if(String.valueOf(holder.like.getTag())=="heart"){

                    holder.like.setTag("heart1");
                    holder.like.setImageResource(R.drawable.ic_baseline_thumb_up_24);
                    String likes = holder.Nlikes.getText().toString();
                    int likess = Integer.parseInt(likes)+1;
                    holder.Nlikes.setText(likess+"");

                }
                else if(String.valueOf(holder.like.getTag())=="heart1"){
                    holder.like.setTag("heart");
                    holder.like.setImageResource(R.drawable.ic_outline_thumb_up_24);
                    String likes = holder.Nlikes.getText().toString();
                    int likess = Integer.parseInt(likes)-1;
                    holder.Nlikes.setText(likess+"");
                }



            }
        }));

        //on click listener for double click on image


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView,userImage,like;
        TextView userName, description,Nlikes;
        ProgressBar progressBar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.post_image);
            userName = itemView.findViewById(R.id.publisher);
            description = itemView.findViewById(R.id.description);
            userImage = itemView.findViewById(R.id.userImage);
            like = itemView.findViewById(R.id.like);
            Nlikes = itemView.findViewById(R.id.Nlikes);
            progressBar = itemView.findViewById(R.id.postImageProgressBar);

        }
    }

}
