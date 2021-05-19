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
import com.example.firebase_login.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatListsAdapter extends RecyclerView.Adapter<ChatListsAdapter.ViewHolder> {


    ArrayList<User> list;
    Context context;


    public ChatListsAdapter(ArrayList<User> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_row_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {


        final User user = list.get(position);
        final String Image_url = user.getImageurl();
        Glide.with(this.context).load(Image_url).into(holder.imageView);
        holder.userName.setText(user.getName());


        //setting the last sent message

        FirebaseDatabase.getInstance().getReference().child("chats")
                .child(FirebaseAuth.getInstance().getUid()+user.getUid())
                .orderByChild("timestamp")
                .limitToLast(1)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren())
                        {
                            for(DataSnapshot snapshot:dataSnapshot.getChildren())
                                holder.lastMessage.setText(snapshot.child("message").getValue(String.class));


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, usersChatActivity.class);
                intent.putExtra("mName",user.getName());
                intent.putExtra("mProfile",Image_url);
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

    ImageView imageView;
    TextView userName, lastMessage;


       public ViewHolder(@NonNull View itemView) {
           super(itemView);
           imageView = itemView.findViewById(R.id.user_image_view);
           userName = itemView.findViewById(R.id.user_name);
           lastMessage = itemView.findViewById(R.id.user_last_message);


       }
   }

}
