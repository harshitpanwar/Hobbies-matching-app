package com.example.firebase_login.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.firebase_login.Adapters.ChatAdapter;
import com.example.firebase_login.Adapters.CommentAdapter;
import com.example.firebase_login.Models.Comment;
import com.example.firebase_login.Models.Messages;
import com.example.firebase_login.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CommentActivity extends AppCompatActivity {

    EditText addcomment;
    FirebaseDatabase database;
    RecyclerView comment_recycler_view;
    ImageView add_comment_button;
    private String postId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        postId = getIntent().getStringExtra("postId");
        addcomment = findViewById(R.id.add_comment);
        database = FirebaseDatabase.getInstance();
        comment_recycler_view = findViewById(R.id.comment_recycler_view);
        add_comment_button = findViewById(R.id.add_comment_button);

        final ArrayList<Comment> comments = new ArrayList<>();

        final CommentAdapter commentAdapter = new CommentAdapter(comments, this);

        comment_recycler_view.setAdapter(commentAdapter);



        database.getReference().child("posts").child(postId).child("comments")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        comments.clear();
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                            Comment comment = snapshot.getValue(Comment.class);


                            comments.add(comment);


                        }


                        commentAdapter.notifyDataSetChanged();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



        add_comment_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                String msg = addcomment.getText().toString();
                String trim = msg.trim();
                if(!trim.equals("")){

                    long millis=System.currentTimeMillis();
                    Comment comment = new Comment(getIntent().getStringExtra("userId"), addcomment.getText().toString());

                    addcomment.setText("");
                    comment_recycler_view.smoothScrollToPosition(comment_recycler_view.getAdapter().getItemCount());

                    database.getReference().child("posts").child(postId)
                            .child("comments")
                            .child(millis+"")
                            .setValue(comment);

                }
                else
                {
                    addcomment.setText("");

                }


            }
        });



    }
}