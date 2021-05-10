package com.example.firebase_login.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.firebase_login.Adapters.ChatListsAdapter;
import com.example.firebase_login.Models.User;
import com.example.firebase_login.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GroupsFragment extends Fragment {


    public GroupsFragment(){

    }
    LottieAnimationView loading;
    ArrayList<User> list = new ArrayList<>();
    FirebaseDatabase database;
    RecyclerView chats_list_RecylerView;
    ChatListsAdapter chatListsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.groups_fragment, container, false);
        chats_list_RecylerView = v.findViewById(R.id.chatsRecyclerView);
        chatListsAdapter = new ChatListsAdapter(list, getContext());
        database = FirebaseDatabase.getInstance();
        chats_list_RecylerView.setAdapter(chatListsAdapter);
        loading = v.findViewById(R.id.loading_animation);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        chats_list_RecylerView.setLayoutManager(layoutManager);


        database.getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot snapshot :dataSnapshot.getChildren()){

                     User user = snapshot.getValue(User.class);
                     user.setUid(snapshot.getKey());
                     list.add(user);

                 }

                loading.setVisibility(View.GONE);
                chats_list_RecylerView.setVisibility(View.VISIBLE);

                 chatListsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return v;
    }
}
