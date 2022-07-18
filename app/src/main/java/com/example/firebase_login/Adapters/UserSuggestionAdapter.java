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
import android.widget.Filter;
import android.widget.Filterable;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class UserSuggestionAdapter extends RecyclerView.Adapter<UserSuggestionAdapter.ViewHolder> implements Filterable {


    ArrayList<User> list;
    ArrayList<User> fullList;
    Context context;


    public UserSuggestionAdapter(ArrayList<User> list, Context context) {
        this.list = list;
        this.context = context;
        fullList = new ArrayList<>(list);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.suggested_person_card,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final User user = list.get(position);

        holder.userName.setText(user.getName());
        Glide.with(this.context)
                .load(user.getImageurl())
                .placeholder(R.drawable.background)
                .into(holder.userImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, usersChatActivity.class);
                intent.putExtra("mName",user.getName());
                intent.putExtra("mProfile",user.getImageurl());
                intent.putExtra("mUid",user.getUid());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        CircleImageView userImage;
        TextView userName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userImage = itemView.findViewById(R.id.suggested_profile_pic);
            userName = itemView.findViewById(R.id.suggested_name);

        }
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<User> filteredList = new ArrayList<>();
            if(charSequence == null || charSequence.length() == 0){
                filteredList.addAll(fullList);
            }
            else{
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (User user: fullList){
                    if (user.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(user);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            list.clear();
            list.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();

        }
    };
}
