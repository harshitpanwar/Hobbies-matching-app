package com.example.firebase_login.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebase_login.Adapters.UsersCategoryAdapter;
import com.example.firebase_login.Models.HobbiesUser;
import com.example.firebase_login.Models.User;
import com.example.firebase_login.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SelectedCategoryActivity extends AppCompatActivity {



    FirebaseDatabase database;
    FirebaseAuth fAuth;
    RecyclerView category_users;
    ArrayList<User> list = new ArrayList<>();
    UsersCategoryAdapter usersCategoryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_category);

        database = FirebaseDatabase.getInstance();
        fAuth = FirebaseAuth.getInstance();
        category_users = findViewById(R.id.users_category);
        usersCategoryAdapter = new UsersCategoryAdapter(list, this);
        category_users.setAdapter(usersCategoryAdapter);
        String category = getIntent().getStringExtra("category");
        TextView category_heading = findViewById(R.id.category_heading);
        category_heading.setText(category);


        database.getReference().child("hobbies")
                .child(category)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot snapshot :dataSnapshot.getChildren()){


                            User hobbiesUser = snapshot.getValue(User.class);
                            if(!hobbiesUser.getUid().equals(fAuth.getCurrentUser().getUid())){
                                list.add(hobbiesUser);

                            }


                        }


                        usersCategoryAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });





    }
}