package com.example.firebase_login.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebase_login.R;
import com.example.firebase_login.Models.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class register extends AppCompatActivity {


    ProgressBar progressBar;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    LinearLayout gButton;
    GoogleSignInClient mGoogleSignInClient;
    SharedPreferences.Editor editor;
    FirebaseAuth fAuth;
    LinearLayout fButton;
    LinearLayout gitButton;
    ImageView logo;
    TextView textView;
    String mMessageId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        progressBar = findViewById(R.id.progress);
        fAuth = FirebaseAuth.getInstance();
        gButton = findViewById(R.id.gSignInButton);
        fButton = findViewById(R.id.fSignInButton);
        gitButton = findViewById(R.id.gitSignInButton);
        logo = findViewById(R.id.logo);
        textView = findViewById(R.id.textView);


        final SharedPreferences sharedPreferences = getSharedPreferences("userdetails", Context.MODE_PRIVATE);

        editor= sharedPreferences.edit();



        if(fAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }


        gButton.setAnimation(AnimationUtils.loadAnimation(this, R.anim.gbutton_animation));
        fButton.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fbutton_animation));
        gitButton.setAnimation(AnimationUtils.loadAnimation(this, R.anim.gitbutton_animation));
        logo.setAnimation(AnimationUtils.loadAnimation(this, R.anim.logo_animation));
        textView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.logo_animation));

        gButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                signIn();
            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


    }



    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 101) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
            }
        }
    }


    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        fAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = fAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {



        String mName = user.getDisplayName();
        String mImage = user.getPhotoUrl().toString();
        String mEmail = user.getEmail();
//
//        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
//            @Override
//            public void onComplete(@NonNull  Task<String> task) {
//                 mMessageId = task.getResult();
//            }
//        });



        editor.putString("name",mName);
        editor.putString("email",mEmail);
        editor.putString("hobbies","00000000");
        editor.putString("imageurl",mImage);
        editor.commit();
        User user1 = new User(fAuth.getUid(), mName, mEmail, mImage,"00000000", mMessageId);



        database.getReference().child("users")
                .child(fAuth.getCurrentUser().getUid())
                .setValue(user1)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = new Intent(getApplicationContext(), Initial_Registration.class);
                        progressBar.setVisibility(View.INVISIBLE);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"could not register the user",Toast.LENGTH_SHORT).show();

                    }
                });

    }

    private void checkEmail(){

        fAuth.fetchSignInMethodsForEmail(fAuth.getCurrentUser().getEmail().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                boolean check = !task.getResult().getSignInMethods().isEmpty();
                if (!check) {

                }
                else {

                }
            }
        }
        );




    }






}