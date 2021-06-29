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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase_login.R;
import com.example.firebase_login.Activities.similar_users_results;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewFragment extends Fragment {

    DatabaseReference reference;
    FirebaseAuth fAuth;
    RecyclerView suggested_people;
    String hobbies;
    String[] hobbies_array;
    TextView user_data;
    ListView hobbies_list_view;
    String[] hobbies_names = {"sports", "coding", "chess", "badminton", "gyming", "stock market", "stand up", "general", };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.view_fragment, container, false);

        reference = FirebaseDatabase.getInstance().getReference().child("users");
        fAuth = FirebaseAuth.getInstance();
        suggested_people = v.findViewById(R.id.suggested_recyclerView);



        suggested_people.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));






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
