package com.example.firebase_login.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.firebase_login.R;
import com.example.firebase_login.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class register extends AppCompatActivity {

    EditText email;
    EditText password;
    EditText name;
    Button register, login;
    ProgressBar progressBar;
    FirebaseDatabase database = FirebaseDatabase.getInstance();


    //this is the firebase authenticator
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);
        register = findViewById(R.id.register_button);
        progressBar = findViewById(R.id.progress);
        fAuth = FirebaseAuth.getInstance();
        login = findViewById(R.id.loginhere);
        final HashMap<String, Object> user_details = new HashMap<>();

        final SharedPreferences sharedPreferences = getSharedPreferences("userdetails", Context.MODE_PRIVATE);

        final SharedPreferences.Editor editor= sharedPreferences.edit();



        if(fAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    final String mEmail = email.getText().toString();
                    String mPassword = password.getText().toString();
                    final String mName = name.getText().toString().trim();

                        if(TextUtils.isEmpty(mEmail)){
                            email.setError("email is required");
                            return;
                        }
                if(TextUtils.isEmpty(mPassword)){
                    password.setError("Password is required");
                    return;
                }

                if(password.length() < 6){
                    password.setError("Password must be greater than 6 characters");
                    return;
                }

                user_details.put("name", mName);
                user_details.put("email", mEmail);
                user_details.put("hobbies", "00000000" );

                progressBar.setVisibility(View.VISIBLE);

                //register the user to firebase

                fAuth.createUserWithEmailAndPassword(mEmail, mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                         if(task.isSuccessful()){
                             Toast.makeText(getApplicationContext(), "account creation succesfull",Toast.LENGTH_SHORT).show();
                             editor.putString("name",mName);
                             editor.putString("email",mEmail);
                             editor.putString("hobbies","00000000");
                             editor.commit();
                             User user = new User(fAuth.getUid(),mName,mEmail,"","00000000");



                             database.getReference().child("users")
                                     .child(fAuth.getCurrentUser().getUid())
                                     .setValue(user)
                                     .addOnSuccessListener(new OnSuccessListener<Void>() {
                                         @Override
                                         public void onSuccess(Void aVoid) {
                                             Intent intent = new Intent(getApplicationContext(), Initial_Registration.class);
                                             progressBar.setVisibility(View.INVISIBLE);
                                             startActivity(intent);
                                             finish();
                                         }
                                     })
                                     .addOnFailureListener(new OnFailureListener() {
                                         @Override
                                         public void onFailure(@NonNull Exception e) {
                                             Toast.makeText(getApplicationContext(),"could not register the user",Toast.LENGTH_SHORT).show();

                                         }
                                     });


                        }

                         else{

                             Toast.makeText(getApplicationContext(), "Error! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();



                         }
                    }
                });

            }
        });

// login here button on click listener


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(getApplicationContext(), loginactivity.class);
                startActivity(intent1);
                finish();
            }
        });










    }
}