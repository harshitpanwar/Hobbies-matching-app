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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.firebase_login.R;
import com.example.firebase_login.Models.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
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
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class register extends AppCompatActivity {
//
//    EditText email;
//    EditText password;
//    EditText name;
//    Button register, login;
    ProgressBar progressBar;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    LinearLayout gButton;
    GoogleSignInClient mGoogleSignInClient;
    SharedPreferences.Editor editor;

    //this is the firebase authenticator
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        email = findViewById(R.id.email);
//        password = findViewById(R.id.password);
//        name = findViewById(R.id.name);
//        register = findViewById(R.id.register_button);
        progressBar = findViewById(R.id.progress);
        fAuth = FirebaseAuth.getInstance();
//        login = findViewById(R.id.loginhere);
        gButton = findViewById(R.id.gSignInButton);


        final HashMap<String, Object> user_details = new HashMap<>();

        final SharedPreferences sharedPreferences = getSharedPreferences("userdetails", Context.MODE_PRIVATE);

        editor= sharedPreferences.edit();



        if(fAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

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






//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                    final String mEmail = email.getText().toString();
//                    String mPassword = password.getText().toString();
//                    final String mName = name.getText().toString().trim();
//
//                        if(TextUtils.isEmpty(mEmail)){
//                            email.setError("email is required");
//                            return;
//                        }
//                if(TextUtils.isEmpty(mPassword)){
//                    password.setError("Password is required");
//                    return;
//                }
//
//                if(password.length() < 6){
//                    password.setError("Password must be greater than 6 characters");
//                    return;
//                }
//
//                user_details.put("name", mName);
//                user_details.put("email", mEmail);
//                user_details.put("hobbies", "00000000" );
//
//                progressBar.setVisibility(View.VISIBLE);
//
//                //register the user to firebase
//
//                fAuth.createUserWithEmailAndPassword(mEmail, mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                         if(task.isSuccessful()){
//                             Toast.makeText(getApplicationContext(), "account creation succesfull",Toast.LENGTH_SHORT).show();
//                             editor.putString("name",mName);
//                             editor.putString("email",mEmail);
//                             editor.putString("hobbies","00000000");
//                             editor.commit();
//                             User user = new User(fAuth.getUid(),mName,mEmail,"","00000000");
//
//
//
//                             database.getReference().child("users")
//                                     .child(fAuth.getCurrentUser().getUid())
//                                     .setValue(user)
//                                     .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                         @Override
//                                         public void onSuccess(Void aVoid) {
//                                             Intent intent = new Intent(getApplicationContext(), Initial_Registration.class);
//                                             progressBar.setVisibility(View.INVISIBLE);
//                                             startActivity(intent);
//                                             finish();
//                                         }
//                                     })
//                                     .addOnFailureListener(new OnFailureListener() {
//                                         @Override
//                                         public void onFailure(@NonNull Exception e) {
//                                             Toast.makeText(getApplicationContext(),"could not register the user",Toast.LENGTH_SHORT).show();
//
//                                         }
//                                     });
//
//
//                        }
//
//                         else{
//
//                             Toast.makeText(getApplicationContext(), "Error! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//
//
//
//                         }
//                    }
//                });
//
//            }
//        });
//
//// login here button on click listener
//
//
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent1 = new Intent(getApplicationContext(), loginactivity.class);
//                startActivity(intent1);
//                finish();
//            }
//        });










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


        editor.putString("name",mName);
        editor.putString("email",mEmail);
        editor.putString("hobbies","00000000");
        editor.putString("imageurl",mImage);
        editor.commit();
        User user1 = new User(fAuth.getUid(), mName, mEmail, mImage,"00000000");



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

}