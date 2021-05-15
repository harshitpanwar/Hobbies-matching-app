package com.example.firebase_login.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.firebase_login.Fragments.GroupsFragment;
import com.example.firebase_login.Fragments.HomeFragment;
import com.example.firebase_login.Fragments.PostFragment;
import com.example.firebase_login.Fragments.ProfileFragment;
import com.example.firebase_login.R;
import com.example.firebase_login.Fragments.ViewFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    MeowBottomNavigation meowBottomNavigation;
    ImageView settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        BottomNavigationView btv = findViewById(R.id.bottom_navigation);
//        btv.setItemBackgroundResource(R.drawable.background);
        meowBottomNavigation = (MeowBottomNavigation)findViewById(R.id.bottom_navigation);
        settings = findViewById(R.id.settings);


        meowBottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_baseline_chat_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_baseline_search_24));

        meowBottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_baseline_home_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(4,R.drawable.add));

        meowBottomNavigation.add(new MeowBottomNavigation.Model(5,R.drawable.ic_baseline_person_24));

//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new HomeFragment()).commit();



        if(savedInstanceState == null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment,new HomeFragment())
                    .commit();


        }



        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Initial_Registration.class);
                startActivity(intent);
            }
        });


        meowBottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {


                Fragment selectedFragment = null;

                switch (item.getId()){

                    case 3:
                        selectedFragment = new HomeFragment();
                        break;

                    case 2:
                        selectedFragment = new ViewFragment();
                        break;

                    case 4:
                        selectedFragment = new PostFragment();
                        break;

                    case 1:
                        selectedFragment = new GroupsFragment();
                        break;

                    case 5:
                        selectedFragment = new ProfileFragment();
                        break;

                }

                loadFragment(selectedFragment);
            }
        });

        meowBottomNavigation.show(3,true);



        meowBottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
//                Toast.makeText(getApplicationContext(),item.getId(),Toast.LENGTH_SHORT).show();
            }
        });

        meowBottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {


                if(item.getId()==3){
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment,new HomeFragment())
                        .commit();

                }


            }
        });


//                btvOnClickListener();

//        btv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//
//                Fragment selectedFragment = null;
//
//                switch (item.getItemId()){
//
//                    case R.id.home:
//                        selectedFragment = new HomeFragment();
//                        break;
//
//                    case R.id.find:
//                        selectedFragment = new GroupsFragment();
//                        break;
//
//                    case R.id.post:
//                        selectedFragment = new PostFragment();
//                        break;
//
//                    case R.id.image:
//                        selectedFragment = new ViewFragment();
//                        break;
//
//                    case R.id.profile:
//                        selectedFragment = new ProfileFragment();
//                        break;

//                }
//
//
//
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, selectedFragment).commit();
//
//
//
//                    return true;
//
//
//
//            }
//        });
    }

    private void btvOnClickListener() {






    }

    private void loadFragment(Fragment fragment) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment,fragment)
                    .commit();


    }
}