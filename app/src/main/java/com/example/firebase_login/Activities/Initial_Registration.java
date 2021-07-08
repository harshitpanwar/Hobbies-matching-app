package com.example.firebase_login.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.firebase_login.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Initial_Registration extends AppCompatActivity {
     String hobbies="";

    GridLayout mainGrid;
    Button save_btn;
    String[] hobbies_array;
    final HashMap<String, Object> user_details = new HashMap<>();
    SharedPreferences sharedPreferences;
    FirebaseAuth fAuth;
    FirebaseDatabase database;
    String[] hobbies_sf_array;
    String activity_name;
    final ArrayList<ChipGroup> chg = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial__registration);
        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        user_details.put("user id",fAuth.getCurrentUser().getUid());
        save_btn = findViewById(R.id.save_btn);
        ChipGroup chg1 = findViewById(R.id.group1);
        ChipGroup chg2 = findViewById(R.id.group2);
        ChipGroup chg3 = findViewById(R.id.group3);
        ChipGroup chg4 = findViewById(R.id.group4);
        ChipGroup chg5 = findViewById(R.id.group5);
        ChipGroup chg6 = findViewById(R.id.group6);

        chg.add(chg1);
        chg.add(chg2);
        chg.add(chg3);
        chg.add(chg4);
        chg.add(chg5);
        chg.add(chg6);

        setInitalvalues();


        save_btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String msg = "";

            for(int j=0;j<6;j++){
            int chipsCount = chg.get(j).getChildCount();
            if (chipsCount == 0) {
                msg = "None!!";
            } else {
                int i = 0;
                while (i < chipsCount) {
                    Chip chip = (Chip) chg.get(j).getChildAt(i);
                    if (chip.isChecked() ) {
                        String hobbyNode = chip.getText().toString();
                        saveDataToFirebase(hobbyNode);
                    }
                    else{
                        String hobbyNode = chip.getText().toString();
                        deleteDataFromFirebase(hobbyNode);

                    }
                    i++;
                };
            }
            // show message
            if(msg=="None!!")
            Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
        }
        }

    });



    }

    private void setInitalvalues() {

        database.getReference().child("users").child(fAuth.getCurrentUser().getUid()).child("hobbies").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    String result = "";
                for(DataSnapshot snapshot :dataSnapshot.getChildren()){
                    String hobby = (String)snapshot.getValue();
                    result = result+hobby;
                    for(int j=0;j<6;j++){
                        int chipsCount = chg.get(j).getChildCount();

                            int i = 0;
                            while (i < chipsCount) {
                                Chip chip = (Chip) chg.get(j).getChildAt(i);
                                String chipInfo = chip.getText().toString();
                                Log.i("chip info", chipInfo);
                                if (chipInfo.equals(hobby)) {
                                    chip.setChecked(true);
                                }
                                i++;
                            }
                        }

                    }

                }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void deleteDataFromFirebase(String hobbyNode) {

        database.getReference().child("hobbies").child(hobbyNode).child(fAuth.getCurrentUser().getUid()).removeValue();
        database.getReference().child("users").child(fAuth.getCurrentUser().getUid()).child("hobbies").child(hobbyNode).removeValue();

    }

    private void saveDataToFirebase(String hobbyNode) {

        database.getReference().child("hobbies").child(hobbyNode).child(fAuth.getCurrentUser().getUid()).setValue(user_details);
        database.getReference().child("users").child(fAuth.getCurrentUser().getUid()).child("hobbies").child(hobbyNode).setValue(hobbyNode);

    }


}