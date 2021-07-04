package com.example.firebase_login.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.firebase_login.Adapters.ChatListsAdapter;
import com.example.firebase_login.Adapters.PostAdapter;
import com.example.firebase_login.Models.Post;
import com.example.firebase_login.Models.User;
import com.example.firebase_login.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class HomeFragment extends Fragment {

    NestedScrollView nestedScrollView;
    ArrayList<Post> list = new ArrayList<>();
    FirebaseDatabase database;
    RecyclerView posts_list_recyclerview;
    PostAdapter postAdapter;
    ProgressBar progressBar;
    ShimmerFrameLayout shimmerFrameLayout;
    LottieAnimationView loading;
    int page = 1, limit = 10;

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.home_fragment_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.home_fragment, container, false);


        posts_list_recyclerview = v.findViewById(R.id.postRecyclerView);
        postAdapter = new PostAdapter(list, getContext());
        database = FirebaseDatabase.getInstance();
        posts_list_recyclerview.setAdapter(postAdapter);

//        progressBar = v.findViewById(R.id.progressbar);

        loading = v.findViewById(R.id.loading_animation);
        final RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getContext());

        database.getReference().child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                list.clear();
                for(DataSnapshot snapshot :dataSnapshot.getChildren()){

                    Post post = snapshot.getValue(Post.class);
                    list.add(post);

                }
                Collections.reverse(list);

//                posts_list_recyclerview.setVisibility(View.VISIBLE);
//                shimmerFrameLayout.stopShimmer();
//                shimmerFrameLayout.setVisibility(View.GONE);
//                progressBar.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                posts_list_recyclerview.setVisibility(View.VISIBLE);
//                posts_list_recyclerview.smoothScrollToPosition(posts_list_recyclerview.getAdapter().getItemCount());
                postAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });








        return v;
    }
}
