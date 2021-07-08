package com.example.firebase_login.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.firebase_login.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial__registration);
//        mainGrid = (GridLayout) findViewById(R.id.mainGrid);
        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
//        sharedPreferences = getSharedPreferences("userdetails", Context.MODE_PRIVATE);
//        user_details.put("email",fAuth.getCurrentUser().getEmail());
        user_details.put("user id",fAuth.getCurrentUser().getUid());
//        user_details.put("name", sharedPreferences.getString("name","error 404"));
//        user_details.put("imageurl", sharedPreferences.getString("image url","noImage"));
//        String hobbies_sf = sharedPreferences.getString("hobbies","00000000");
//        hobbies_sf_array = hobbies_sf.split("");
//        final SharedPreferences sharedPreferences = getSharedPreferences("userdetails", Context.MODE_PRIVATE);
//
//        final SharedPreferences.Editor editor= sharedPreferences.edit();
//        activity_name = getIntent().getStringExtra("activity");
//
//        setInitialknownValuesOfCardView();
//
//        //Set Event
////        setSingleEvent(mainGrid);
//        setToggleEvent(mainGrid);
//        final FirebaseAuth fAuth = FirebaseAuth.getInstance();
//        save_btn = findViewById(R.id.save_button);
//        save_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                    hobbies = "";
//                for(int j=0;j<mainGrid.getChildCount();j++){
//                    final CardView cardView = (CardView) mainGrid.getChildAt(j);
//
//                    if (cardView.getCardBackgroundColor().getDefaultColor() == -1) {
//                        hobbies = hobbies + "0";
//                    }
//                    else
//                    {
//                        hobbies = hobbies + "1";
//                    }
//
//                }
//
//                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(fAuth.getCurrentUser().getUid()).child("hobbies");
//                databaseReference.setValue(hobbies);
//
//
//                hobbies_array = hobbies.split("");
//                saveTheDataToFirebase();
//                editor.putString("hobbies",hobbies);
//                editor.apply();
//
//                Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_SHORT).show();
//
//                Intent intent = new Intent(Initial_Registration.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//
//
//            }
//        });
//




    save_btn = findViewById(R.id.save_btn);
    save_btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String msg = "";
            ArrayList<ChipGroup> chg = new ArrayList<>();

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
        }}

    });



    }

    private void deleteDataFromFirebase(String hobbyNode) {

        database.getReference().child("hobbies").child(hobbyNode).child(fAuth.getCurrentUser().getUid()).removeValue();


    }

    private void saveDataToFirebase(String hobbyNode) {

        database.getReference().child("hobbies").child(hobbyNode).child(fAuth.getCurrentUser().getUid()).setValue(user_details);


    }

    private void setInitialknownValuesOfCardView() {


        if (hobbies_sf_array[0].equals("1")){
            CardView view = (CardView) mainGrid.getChildAt(0);
            view.setCardBackgroundColor(Color.parseColor("#E6F7FF"));
        }

        if (hobbies_sf_array[1].equals("1"))
        {
            CardView view = (CardView) mainGrid.getChildAt(1);
            view.setCardBackgroundColor(Color.parseColor("#E6F7FF"));

        }
        if (hobbies_sf_array[2].equals("1"))
        {
            CardView view = (CardView) mainGrid.getChildAt(2);
            view.setCardBackgroundColor(Color.parseColor("#E6F7FF"));
        }
        if (hobbies_sf_array[3].equals("1"))
        {
            CardView view = (CardView) mainGrid.getChildAt(3);
            view.setCardBackgroundColor(Color.parseColor("#E6F7FF"));
        }
        if (hobbies_sf_array[4].equals("1"))
        {
            CardView view = (CardView) mainGrid.getChildAt(4);
            view.setCardBackgroundColor(Color.parseColor("#E6F7FF"));
        }
        if (hobbies_sf_array[5].equals("1"))
        {
            CardView view = (CardView) mainGrid.getChildAt(5);
            view.setCardBackgroundColor(Color.parseColor("#E6F7FF"));
        }
        if (hobbies_sf_array[6].equals("1"))
        {
            CardView view = (CardView) mainGrid.getChildAt(6);
            view.setCardBackgroundColor(Color.parseColor("#E6F7FF"));
        }
        if (hobbies_sf_array[7].equals("1"))
            {
                CardView view = (CardView) mainGrid.getChildAt(7);
                view.setCardBackgroundColor(Color.parseColor("#E6F7FF"));
            }


    }

    private void saveTheDataToFirebase() {

        if (hobbies_array[0].equals("1"))
            database.getReference().child("hobbies").child("sports").child(fAuth.getCurrentUser().getUid()).setValue(user_details);
        else
            database.getReference().child("hobbies").child("sports").child(fAuth.getCurrentUser().getUid()).removeValue();

        if (hobbies_array[1].equals("1"))
            database.getReference().child("hobbies").child("coding").child(fAuth.getCurrentUser().getUid()).setValue(user_details);
        else
            database.getReference().child("hobbies").child("coding").child(fAuth.getCurrentUser().getUid()).removeValue();

        if (hobbies_array[2].equals("1"))
            database.getReference().child("hobbies").child("chess").child(fAuth.getCurrentUser().getUid()).setValue(user_details);
        else
            database.getReference().child("hobbies").child("chess").child(fAuth.getCurrentUser().getUid()).removeValue();

        if (hobbies_array[3].equals("1"))
            database.getReference().child("hobbies").child("badminton").child(fAuth.getCurrentUser().getUid()).setValue(user_details);
        else
            database.getReference().child("hobbies").child("badminton").child(fAuth.getCurrentUser().getUid()).removeValue();

        if (hobbies_array[4].equals("1"))
            database.getReference().child("hobbies").child("gyming").child(fAuth.getCurrentUser().getUid()).setValue(user_details);
        else
            database.getReference().child("hobbies").child("gyming").child(fAuth.getCurrentUser().getUid()).removeValue();

        if (hobbies_array[5].equals("1"))
            database.getReference().child("hobbies").child("stock market").child(fAuth.getCurrentUser().getUid()).setValue(user_details);
        else
            database.getReference().child("hobbies").child("stock market").child(fAuth.getCurrentUser().getUid()).removeValue();

        if (hobbies_array[6].equals("1"))
            database.getReference().child("hobbies").child("standup").child(fAuth.getCurrentUser().getUid()).setValue(user_details);
        else
            database.getReference().child("hobbies").child("standup").child(fAuth.getCurrentUser().getUid()).removeValue();

        if (hobbies_array[7].equals("1"))
            database.getReference().child("hobbies").child("general").child(fAuth.getCurrentUser().getUid()).setValue(user_details);
        else
            database.getReference().child("hobbies").child("general").child(fAuth.getCurrentUser().getUid()).removeValue();



    }

    private void setToggleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView


            final CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cardView.getCardBackgroundColor().getDefaultColor() == -1) {
                        //Change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#E6F7FF"));

                    } else {
                        //Change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    }
                }
            });





        }
    }
}