package com.example.firebase_login.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.firebase_login.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Hobbies_update_activity extends AppCompatActivity {



    CheckBox hbs_sports;
    CheckBox hbs_badminton;
    CheckBox hbs_coding;
    CheckBox hbs_gyming;
    CheckBox hbs_chess ;
    CheckBox hbs_stock_market ;
    CheckBox hbs_standup;
    CheckBox hbs_general;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseDatabase database2 = FirebaseDatabase.getInstance();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    FirebaseAuth fAuth;

    final HashMap<String, Object> user_details = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobbies_update_activity);

         fAuth = FirebaseAuth.getInstance();
         hbs_sports = findViewById(R.id.hbs_sports);
         hbs_coding = findViewById(R.id.hbs_coding);
         hbs_chess = findViewById(R.id.hbs_chess);
         hbs_badminton = findViewById(R.id.hbs_badminton);
         hbs_gyming = findViewById(R.id.hbs_gyming);
         hbs_stock_market = findViewById(R.id.hbs_stock_market);
         hbs_standup = findViewById(R.id.hbs_standUp);
         hbs_general = findViewById(R.id.hbs_general);
         sharedPreferences = getSharedPreferences("userdetails", Context.MODE_PRIVATE);
         editor = sharedPreferences.edit();
        Button save_button = findViewById(R.id.save_button);
        final ProgressBar progressBar = findViewById(R.id.progress);


        String hobbies_sf = sharedPreferences.getString("hobbies","error");
        String[] hobbies_array = hobbies_sf.split("");

        user_details.put("email",FirebaseAuth.getInstance().getCurrentUser().getEmail());
        user_details.put("user id",FirebaseAuth.getInstance().getCurrentUser().getUid());
        user_details.put("name", sharedPreferences.getString("name","error 404"));
        user_details.put("imageurl", sharedPreferences.getString("image url","noImage"));
        try {
            if (hobbies_array[0].equals("1"))
                hbs_sports.setChecked(true);
            if (hobbies_array[1].equals("1"))
                hbs_coding.setChecked(true);
            if (hobbies_array[2].equals("1"))
                hbs_chess.setChecked(true);
            if (hobbies_array[3].equals("1"))
                hbs_badminton.setChecked(true);
            if (hobbies_array[4].equals("1"))
                hbs_gyming.setChecked(true);
            if (hobbies_array[5].equals("1"))
                hbs_stock_market.setChecked(true);
            if (hobbies_array[6].equals("1"))
                hbs_standup.setChecked(true);
            if (hobbies_array[7].equals("1"))
                hbs_general.setChecked(true);
        }
        catch(Exception e){

                if(hobbies_sf.equals(""))
                    Toast.makeText(getApplicationContext(),"pakad liya lmao",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),hobbies_sf,Toast.LENGTH_SHORT).show();

                }

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);

                saving_the_data_to_firebase_and_shared_preferences();

                progressBar.setVisibility(View.INVISIBLE);
            }
        });



    }

    private void saving_the_data_to_firebase_and_shared_preferences() {

        String user_hobbies = "";
        String user_hobbies_names="";
        String user_image =  sharedPreferences.getString("image url","noImage");


        // for each checkbox checked, accumulate a category id in the list
        if (hbs_sports.isChecked()) {
            user_hobbies=user_hobbies+"1";
            user_hobbies_names = user_hobbies_names+"sports ";
            database.getReference().child("hobbies").child("sports").child(fAuth.getCurrentUser().getUid()).setValue(user_details);

        }
        else {
            user_hobbies = user_hobbies + "0";
            database.getReference().child("hobbies").child("sports").child(fAuth.getCurrentUser().getUid()).removeValue();

        }

        if (hbs_coding.isChecked()) {
            user_hobbies=user_hobbies+"1";
            user_hobbies_names = user_hobbies_names+"coding ";
            database.getReference().child("hobbies").child("coding").child(fAuth.getCurrentUser().getUid()).setValue(user_details);

        }
        else
        {
            user_hobbies = user_hobbies + "0";
            database.getReference().child("hobbies").child("coding").child(fAuth.getCurrentUser().getUid()).removeValue();

        }
        if (hbs_chess.isChecked()) {

            user_hobbies =user_hobbies+"1";
            user_hobbies_names = user_hobbies_names+"chess ";
            database.getReference().child("hobbies").child("chess").child(fAuth.getCurrentUser().getUid()).setValue(user_details);

        }
        else
        {
            user_hobbies = user_hobbies + "0";
            database.getReference().child("hobbies").child("chess").child(fAuth.getCurrentUser().getUid()).removeValue();

        }
        if (hbs_badminton.isChecked()) {
            user_hobbies=user_hobbies+"1";
            user_hobbies_names = user_hobbies_names+"badminton ";
            database.getReference().child("hobbies").child("badminton").child(fAuth.getCurrentUser().getUid()).setValue(user_details);

        }
        else
        {
            user_hobbies = user_hobbies + "0";
            database.getReference().child("hobbies").child("badminton").child(fAuth.getCurrentUser().getUid()).removeValue();

        }


        if (hbs_gyming.isChecked()) {
            user_hobbies=user_hobbies+"1";
            user_hobbies_names = user_hobbies_names+"gyming ";
            database.getReference().child("hobbies").child("gyming").child(fAuth.getCurrentUser().getUid()).setValue(user_details);

        }
        else
        {
            user_hobbies = user_hobbies + "0";
            database.getReference().child("hobbies").child("gyming").child(fAuth.getCurrentUser().getUid()).removeValue();

        }
        if (hbs_stock_market.isChecked()) {
            user_hobbies=user_hobbies+"1";
            user_hobbies_names = user_hobbies_names+"stockmarket ";
            database.getReference().child("hobbies").child("stock market").child(fAuth.getCurrentUser().getUid()).setValue(user_details);

        }
        else
        {
            user_hobbies = user_hobbies + "0";
            database.getReference().child("hobbies").child("stock market").child(fAuth.getCurrentUser().getUid()).removeValue();

        }


        if (hbs_standup.isChecked()) {
            user_hobbies=user_hobbies+"1";
            user_hobbies_names = user_hobbies_names+"standup ";
            database.getReference().child("hobbies").child("standup").child(fAuth.getCurrentUser().getUid()).setValue(user_details);

        }
        else
        {
            user_hobbies = user_hobbies + "0";
            database.getReference().child("hobbies").child("standup").child(fAuth.getCurrentUser().getUid()).removeValue();

        }
        if (hbs_general.isChecked()) {
            user_hobbies=user_hobbies+"1";
            user_hobbies_names = user_hobbies_names+"general ";
            database.getReference().child("hobbies").child("general").child(fAuth.getCurrentUser().getUid()).setValue(user_details);

        }
        else
        {
            user_hobbies = user_hobbies + "0";
            database.getReference().child("hobbies").child("general").child(fAuth.getCurrentUser().getUid()).removeValue();

        }




        user_details.put("hobbies", user_hobbies );
        editor.putString("hobbies", user_hobbies);
        editor.putString("hobbies_names",user_hobbies_names);

        editor.commit();

        database2.getReference().child("users").child(fAuth.getCurrentUser().getUid()).child("hobbies").setValue(user_hobbies);

        Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();

     //   String[] interest = interestList.toArray(new String[interestList.size()]);

    //storing user list on the database




    }


}