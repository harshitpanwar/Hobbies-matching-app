package com.example.firebase_login.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.firebase_login.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginactivity extends AppCompatActivity {


     EditText email,password;
     Button login, register;
     ProgressBar progressBar;
     FirebaseAuth fAuth;
     FirebaseDatabase database = FirebaseDatabase.getInstance();
     String mmHobbies;
     String zeroes = "00000000";
     Intent intent_to_main_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login_button);
        register = findViewById(R.id.registerhere);
        progressBar = findViewById(R.id.progress);
        fAuth = FirebaseAuth.getInstance();
        final SharedPreferences sharedPreferences = getSharedPreferences("userdetails", Context.MODE_PRIVATE);

        final SharedPreferences.Editor editor= sharedPreferences.edit();



        if(fAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String mEmail = email.getText().toString();
                String mPassword = password.getText().toString();

                if(TextUtils.isEmpty(mEmail)){
                    email.setError("This field cannot be empty");
                    return;
                }

                if(TextUtils.isEmpty(mPassword)){
                    password.setError("Enter Password");
                    return;
                }

                                progressBar.setVisibility(View.VISIBLE);

                //user authentication is done here


                                fAuth.signInWithEmailAndPassword(mEmail, mPassword)
                                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child(fAuth.getUid());
                                                    reference.addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                            if (dataSnapshot.exists()) {

                                                                try {
                                                                    String mName = (String) dataSnapshot.child("name").getValue();
                                                                    String mHobbies = (String) dataSnapshot.child("hobbies").getValue();
                                                                    String mUrl = (String) dataSnapshot.child("imageurl").getValue();
                                                                    mmHobbies = mHobbies;
                                                                    editor.putString("name", mName);
                                                                    editor.putString("email", mEmail);
                                                                    editor.putString("hobbies", mHobbies);
                                                                    editor.putString("imageurl",mUrl);
//                                                                    Log.d("hobbies ",mHobbies);
                                                                    editor.commit();

                                                                }
                                                                catch (Exception e){


                                                                }


                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    });






                                                    intent_to_main_activity = new Intent(getApplicationContext(), MainActivity.class);


                                                    startActivity(intent_to_main_activity);
                                                    finish();

                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Login was unSuccessful", Toast.LENGTH_SHORT).show();

                                                }
                                            }


                                        });

                    }


        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), com.example.firebase_login.Activities.register.class);
                startActivity(intent);
                finish();

            }
        });






    }


}

