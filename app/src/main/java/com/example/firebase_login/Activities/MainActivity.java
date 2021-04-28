package com.example.firebase_login.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.firebase_login.Fragments.GroupsFragment;
import com.example.firebase_login.Fragments.HomeFragment;
import com.example.firebase_login.Fragments.ProfileFragment;
import com.example.firebase_login.R;
import com.example.firebase_login.Fragments.ViewFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView btv = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new HomeFragment()).commit();
        btv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                Fragment selectedFragment = null;

                switch (item.getItemId()){

                    case R.id.home:
                        selectedFragment = new HomeFragment();
                        break;

                    case R.id.find:
                        selectedFragment = new GroupsFragment();
                        break;

                    case R.id.image:
                        selectedFragment = new ViewFragment();
                        break;

                    case R.id.profile:
                        selectedFragment = new ProfileFragment();
                        break;

                }



                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, selectedFragment).commit();



                    return true;



            }
        });
    }
}