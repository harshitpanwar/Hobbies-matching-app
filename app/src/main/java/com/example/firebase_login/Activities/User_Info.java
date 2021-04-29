package com.example.firebase_login.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.firebase_login.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class User_Info extends AppCompatActivity {



    TextView user_name;
    TextView user_email;
    CircleImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__info);


        user_name = findViewById(R.id.user_name);
        user_email = findViewById(R.id.user_email);
        ImageView message_icon = findViewById(R.id.message_button);
        circleImageView = findViewById(R.id.user_image);



        user_name.setText(getIntent().getStringExtra("username"));
        user_email.setText(getIntent().getStringExtra("useremail"));
        Glide.with(this).load(getIntent().getStringExtra("userImageLink")).into(circleImageView);


        message_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(User_Info.this, usersChatActivity.class);
                intent.putExtra("mName",getIntent().getStringExtra("username"));
                intent.putExtra("mProfile",getIntent().getStringExtra("userImageLink"));
                intent.putExtra("mUid",getIntent().getStringExtra("userid"));
                startActivity(intent);
            }
        });


    }
}