package com.example.firebase_login.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firebase_login.Activities.PostActivity;
import com.example.firebase_login.R;
import com.google.firebase.storage.StorageReference;

public class PostFragment extends Fragment {



    public PostFragment() {
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.home_fragment_menu,menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_post, container, false);

        Intent intent = new Intent(getActivity(), PostActivity.class);
        startActivity(intent);
        getActivity().finish();

        return v;
    }



}