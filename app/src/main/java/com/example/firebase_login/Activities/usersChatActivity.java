package com.example.firebase_login.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.firebase_login.Adapters.ChatAdapter;
import com.example.firebase_login.Models.Messages;
import com.example.firebase_login.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class usersChatActivity extends AppCompatActivity {


    FirebaseDatabase database;
    FirebaseAuth auth;
    CircleImageView userImage;
    TextView userName;
    ImageView backbtn;
    LottieAnimationView loading;
    RecyclerView chatRecyclerView;
    ImageView send_message;
    TextView mMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_chat);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        final String senderId = auth.getUid();
        String recieverId = getIntent().getStringExtra("mUid");
        String mName = getIntent().getStringExtra("mName");
        String mProfile = getIntent().getStringExtra("mProfile");

        userImage = findViewById(R.id.user_image);
        userName = findViewById(R.id.user_name);
        backbtn = findViewById(R.id.back_button);
        chatRecyclerView = findViewById(R.id.chatRecyclerview);
        send_message = findViewById(R.id.send_image_button);
        mMessage = findViewById(R.id.message_box);
        loading = findViewById(R.id.loading_animation);

        Glide.with(this).load(mProfile).into(userImage);
        userName.setText(mName);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(usersChatActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        final ArrayList<Messages> messageModels = new ArrayList<>();

        final ChatAdapter chatAdapter = new ChatAdapter(messageModels, this);

        chatRecyclerView.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        chatRecyclerView.setLayoutManager(layoutManager);

        final String senderRoom = senderId+recieverId;
        final String reciverRoom = recieverId + senderId;


        database.getReference().child("chats")
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        messageModels.clear();
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                            Messages model = snapshot.getValue(Messages.class);


                            messageModels.add(model);


                        }
                        if(!(messageModels.size() ==0)) {
                            chatRecyclerView.smoothScrollToPosition(chatRecyclerView.getAdapter().getItemCount());
                        }


                        loading.setVisibility(View.GONE);
                        chatRecyclerView.setVisibility(View.VISIBLE);
                        chatAdapter.notifyDataSetChanged();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



        send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String msg = mMessage.getText().toString();
                String trim = msg.trim();
                if(!trim.equals("")){
                    final Messages messageModel = new Messages(senderId,trim);
                    messageModel.setTimestamp(new Date().getTime());
                    mMessage.setText("");
                    chatRecyclerView.smoothScrollToPosition(chatRecyclerView.getAdapter().getItemCount());

                    database.getReference().child("chats")
                            .child(senderRoom)
                            .push()
                            .setValue(messageModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {


                            database.getReference().child("chats")
                                    .child(reciverRoom)
                                    .push()
                                    .setValue(messageModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            });



                        }
                    });
                }
                else
                {
                    mMessage.setText("");

                }


            }
        });
        

    }
}