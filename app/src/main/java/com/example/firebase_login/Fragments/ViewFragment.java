package com.example.firebase_login.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase_login.Activities.MainActivity;
import com.example.firebase_login.Adapters.UserSuggestionAdapter;
import com.example.firebase_login.Models.Post;
import com.example.firebase_login.Models.User;
import com.example.firebase_login.R;
import com.example.firebase_login.Activities.similar_users_results;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewFragment extends Fragment {

    FirebaseDatabase database;
    FirebaseAuth fAuth;
    RecyclerView suggested_people;
    UserSuggestionAdapter userSuggestionAdapter;
    ArrayList<User> list = new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.view_fragment, container, false);

        fAuth = FirebaseAuth.getInstance();
        suggested_people = v.findViewById(R.id.suggested_recyclerView);
        suggested_people.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        userSuggestionAdapter = new UserSuggestionAdapter(list, getContext());
        database = FirebaseDatabase.getInstance();
        suggested_people.setAdapter(userSuggestionAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        suggested_people.setLayoutManager(layoutManager);



        database.getReference().child("users")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        list.clear();
                        for(DataSnapshot snapshot :dataSnapshot.getChildren()){

                            User user = snapshot.getValue(User.class);
                            list.add(user);

                        }

                        userSuggestionAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        Toast.makeText(getContext(), "Something went wrong ", Toast.LENGTH_LONG).show();

                    }
                });








//        user_data = v.findViewById(R.id.user_data);
//
//        hobbies_list_view = v.findViewById(R.id.hobbies_list_view);
//        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("userdetails", Context.MODE_PRIVATE);
//        ArrayList<String> user_final_hobbies = new ArrayList<String>();

//
//                 ArrayAdapter<String> hobbies_adapter;
//
//                String hobbies_sf = sharedPreferences.getString("hobbies","error");
//                 hobbies_array = hobbies_sf.split("");
//
//        for(int i = 0;i<hobbies_array.length;i++)
//        {
//            if(hobbies_array[i].equals("1"))
//            {
//                user_final_hobbies.add(hobbies_names[i]);
//            }
//        }
//
//
//
//        final String[] interest = user_final_hobbies.toArray(new String[user_final_hobbies.size()]);
//

//
//
//                 hobbies_adapter = new ArrayAdapter<String>(
//                        getActivity(),
//                        android.R.layout.simple_list_item_1,
//                        interest
//                        );
//
//                        hobbies_list_view.setAdapter(hobbies_adapter);
//        String[] menuitems = {
//                "hello",
//                "hi everyone",
//                "hello world!"
//        };

//            hobbies_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View v, int i, long l) {
//
//                    Intent intent = new Intent(getContext(), similar_users_results.class);
//                    intent.putExtra("HOBBY", interest[i]);
//                    startActivity(intent);
//
//
//                }
//            });



        return v;
    }




}
