package com.example.firebase_login.Fragments;

import android.app.SearchManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.L;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.firebase_login.Adapters.CategoryAdapter;
import com.example.firebase_login.Adapters.UserSuggestionAdapter;
import com.example.firebase_login.Models.User;
import com.example.firebase_login.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.SEARCH_SERVICE;

public class ViewFragment extends Fragment {

    FirebaseDatabase database;
    FirebaseAuth fAuth;
    RecyclerView suggested_people, category_recycler_view;
    UserSuggestionAdapter userSuggestionAdapter;

    ArrayList<User> list = new ArrayList<>();
    ArrayList<User> suggested_users_list = new ArrayList<>();
    ArrayList<String> category_list = new ArrayList<>();
    private ChipGroup chipGroup;
    TextView testt;
    Chip all_chip,suggested_chip,basketball_chip;
    String uid = FirebaseAuth.getInstance().getUid();
    ArrayList<String> result = new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.view_fragment, container, false);
        fAuth = FirebaseAuth.getInstance();
        suggested_people = v.findViewById(R.id.suggested_recyclerView);
        category_recycler_view = v.findViewById(R.id.category_recycler_view);
        userSuggestionAdapter = new UserSuggestionAdapter(list, getContext());
        final CategoryAdapter categoryAdapter = new CategoryAdapter(category_list, getContext());
        database = FirebaseDatabase.getInstance();
        suggested_people.setAdapter(userSuggestionAdapter);
        category_recycler_view.setAdapter(categoryAdapter);
        testt = v.findViewById(R.id.testt);
        category_recycler_view = v.findViewById(R.id.category_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager categoryManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        suggested_people.setLayoutManager(layoutManager);
        category_recycler_view.setLayoutManager(categoryManager);

        database.getReference().child("users_hobbies")
                .child(fAuth.getCurrentUser().getUid())
                .child("hobbies")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        for(DataSnapshot snapshot :dataSnapshot.getChildren()){

                            category_list.add(snapshot.getValue(String.class));


                        }
                        categoryAdapter.notifyDataSetChanged();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });




        getAllUsers();


        return v;
    }

    private void getUserByHobby(String hobby) {


        database.getReference().child("hobbies").child(hobby)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        list.clear();
                        for(DataSnapshot snapshot :dataSnapshot.getChildren()){

                            getUserFromUid(snapshot.getValue().toString());

                        }

                        userSuggestionAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        Toast.makeText(getContext(), "Something went wrong ", Toast.LENGTH_LONG).show();

                    }
                });


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

                        result.clear();


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
                        testt.setText("error");

                    }
                });

// Access the RequestQueue through your singleton class.
        queue.add(jsonObjectRequest);




    }

    private void load_suggested_data() {


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
                        if (!list.contains(user)){
                            list.add(user);
//                        Log.i("user",user.getEmail());
//                        Log.i("list", list.get(0)+"");
                            userSuggestionAdapter.notifyDataSetChanged();
                        }


                    }



                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        Toast.makeText(getContext(), "Something went wrong ", Toast.LENGTH_LONG).show();

                    }
                });




    }





}
