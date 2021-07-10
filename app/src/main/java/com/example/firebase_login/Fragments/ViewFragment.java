package com.example.firebase_login.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.firebase_login.Activities.MainActivity;
import com.example.firebase_login.Adapters.UserSuggestionAdapter;
import com.example.firebase_login.Models.Post;
import com.example.firebase_login.Models.User;
import com.example.firebase_login.R;
import com.example.firebase_login.Activities.similar_users_results;
import com.google.android.gms.flags.Singletons;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewFragment extends Fragment {

    FirebaseDatabase database;
    FirebaseAuth fAuth;
    RecyclerView suggested_people;
    UserSuggestionAdapter userSuggestionAdapter;
    ArrayList<User> list = new ArrayList<>();
    ArrayList<User> suggested_users_list = new ArrayList<>();
    private ChipGroup chipGroup;
    TextView testt;
    Chip all_chip,suggested_chip,basketball_chip;
    String uid = FirebaseAuth.getInstance().getUid();
    ArrayList<String> result = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.view_fragment, container, false);
        chipGroup = v.findViewById(R.id.selectionChipGroup);
        fAuth = FirebaseAuth.getInstance();
        suggested_people = v.findViewById(R.id.suggested_recyclerView);
        suggested_people.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        userSuggestionAdapter = new UserSuggestionAdapter(list, getContext());
        database = FirebaseDatabase.getInstance();
        suggested_people.setAdapter(userSuggestionAdapter);
        testt = v.findViewById(R.id.testt);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        suggested_chip = v.findViewById(R.id.suggested_chip);
        all_chip = v.findViewById(R.id.all_chip);
        basketball_chip=v.findViewById(R.id.basketball_chip);
        suggested_people.setLayoutManager(layoutManager);


        all_chip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAllUsers();
            }
        });

        suggested_chip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load_suggested_data();

            }
        });

        basketball_chip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                testt.setText("basketball clicked");
            }
        });




        return v;
    }


    public void getAllUsers(){

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

    }




    public void getApi(){

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());

        String url ="https://dark-memer.as.r.appspot.com/predict/"+uid;


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            result.add(response.get("0").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            result.add(response.get("1").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            result.add(response.get("2").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            result.add(response.get("3").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            result.add(response.get("4").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        testt.setText(result.size()+"");
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });

// Access the RequestQueue through your singleton class.
        queue.add(jsonObjectRequest);




    }



    private void load_suggested_data() {

        list.clear();

        getApi();
        for(int i=0;i<result.size();i++){
            getUserFromUid(result.get(i));
        }
        Log.i("lol", result.size()+"");





    }

    private void getUserFromUid(String uid) {

        database.getReference().child("users").child(uid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        User user = dataSnapshot.getValue(User.class);
                        list.add(user);
                        Log.i("user",user.getEmail());
                        Log.i("list", list.get(0)+"");
                        userSuggestionAdapter.notifyDataSetChanged();

                    }



                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        Toast.makeText(getContext(), "Something went wrong ", Toast.LENGTH_LONG).show();

                    }
                });




    }





}
