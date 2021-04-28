package com.example.firebase_login.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.firebase_login.Adapters.RecyclerAdapter;
import com.example.firebase_login.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class similar_users_results extends AppCompatActivity {

//ListView common_hobby_users_list ;
FirebaseDatabase database;
DatabaseReference reference;
DatabaseReference ImageReference;
FirebaseAuth fAuth;
RecyclerView recyclerView;
RecyclerAdapter recyclerAdapter;
//ArrayAdapter<String> arrayAdapter;
ArrayList<String> user_names_arraylist = new ArrayList<>();
ArrayList<String> user_emails_arraylist = new ArrayList<>();
ArrayList<String> user_id_arraylist = new ArrayList<>();
ArrayList<String> user_images = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar_users_results);

        final String hobby = getIntent().getStringExtra("HOBBY");
        TextView hobby_text = findViewById(R.id.hobby);
        hobby_text.setText(hobby);
        recyclerView = findViewById(R.id.myrecyclerview);
        recyclerAdapter = new RecyclerAdapter(user_names_arraylist, user_emails_arraylist, user_id_arraylist,user_images, this);
        recyclerView.setAdapter(recyclerAdapter);
//        common_hobby_users_list = findViewById(R.id.common_users_list);
        database = FirebaseDatabase.getInstance();
        ImageReference = FirebaseDatabase.getInstance().getReference("users");
//        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, user_names_arraylist);
        reference = FirebaseDatabase.getInstance().getReference("hobbies").child(hobby);
        fAuth = FirebaseAuth.getInstance();
        final HashMap<String, Object>[] user_details = new HashMap[]{new HashMap<>()};
//        common_hobby_users_list.setAdapter(arrayAdapter);





        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                String curr_user = (String) map.get("user id");
                String curr_user1 = fAuth.getCurrentUser().getUid();


                if(!curr_user.equals(curr_user1))  {
                user_names_arraylist.add((String) map.get("name"));
                user_emails_arraylist.add((String)map.get("email"));
                user_id_arraylist.add((String)map.get("user id"));
//                String comp = "noImage";
//                String comp1 =(String)map.get("imageurl");

//                if(!comp1.equals(comp)){

                    user_images.add((String)map.get("imageurl"));

//                }



                }
                Log.d("hash map", "Value is: " + map);


                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                  recyclerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




//        common_hobby_users_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                Intent intent = new Intent(getApplicationContext(), User_Info.class);
//                intent.putExtra("username", user_names_arraylist.get(i));
//                intent.putExtra("useremail",user_emails_arraylist.get(i));
//                intent.putExtra("userid", user_id_arraylist.get(i));
//                startActivity(intent);
//
//            }
//        });










    }

//    private void array_adapter_inflater(String[] data) {
//
//         arrayAdapter= new ArrayAdapter<>(this,
//                android.R.layout.simple_list_item_1,
//                data);
//        common_hobby_users_list.setAdapter(arrayAdapter);
//
//    }
}