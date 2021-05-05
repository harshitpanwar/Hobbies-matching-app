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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Initial_Registration extends AppCompatActivity {
     String hobbies="";

    GridLayout mainGrid;
    Button save_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial__registration);
        mainGrid = (GridLayout) findViewById(R.id.mainGrid);
        getSupportActionBar().hide();


        final SharedPreferences sharedPreferences = getSharedPreferences("userdetails", Context.MODE_PRIVATE);

        final SharedPreferences.Editor editor= sharedPreferences.edit();




        //Set Event
//        setSingleEvent(mainGrid);
        setToggleEvent(mainGrid);
        final FirebaseAuth fAuth = FirebaseAuth.getInstance();
        save_btn = findViewById(R.id.save_button);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    hobbies = "";
                for(int j=0;j<mainGrid.getChildCount();j++){
                    final CardView cardView = (CardView) mainGrid.getChildAt(j);

                    if (cardView.getCardBackgroundColor().getDefaultColor() == -1) {
                        hobbies = hobbies + "0";
                    }
                    else
                    {
                        hobbies = hobbies + "1";
                    }

                }

                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(fAuth.getCurrentUser().getUid()).child("hobbies");
                databaseReference.setValue(hobbies);


                editor.putString("hobbies",hobbies);
                editor.apply();



                Toast.makeText(getApplicationContext(),hobbies,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Initial_Registration.this, MainActivity.class);
                startActivity(intent);


            }
        });





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