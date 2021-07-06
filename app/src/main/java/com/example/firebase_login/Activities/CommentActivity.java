package com.example.firebase_login.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.firebase_login.R;

public class CommentActivity extends AppCompatActivity {

    EditText addcomment;

    private String postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        postId = getIntent().getStringExtra("postId");
        addcomment = findViewById(R.id.add_comment);

        addcomment.setText(postId);



    }
}