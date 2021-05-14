package com.example.firebase_login.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.firebase_login.Activities.Hobbies_update_activity;
//import com.example.firebase_login.Information;
import com.example.firebase_login.Activities.Initial_Registration;
import com.example.firebase_login.Activities.register;
import com.example.firebase_login.Models.User;
import com.example.firebase_login.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {

    Button edit_hobbies;
    EditText user_name;
    TextView user_email;
    DatabaseReference reference;
    FirebaseDatabase update = FirebaseDatabase.getInstance();
    StorageReference mStorageRef;
    DatabaseReference mDatabaseRef;
    FirebaseAuth fAuth;
    Button save_button;
    Button select_image;
//    Button upload_image;
    private Uri imageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    CircleImageView image;


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.home_fragment_menu,menu);

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout_menu:
                fAuth.signOut();
                Intent intent = new Intent(getContext(), register.class);
                startActivity(intent);
                getActivity().finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);


        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

      View v = inflater.inflate(R.layout.profile_fragment, container, false);

        final SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("userdetails", Context.MODE_PRIVATE);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        setHasOptionsMenu(true);
        edit_hobbies = v.findViewById(R.id.edit_hobbies);
        user_name = v.findViewById(R.id.user_name);
        user_email = v.findViewById(R.id.user_email);
        fAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("users");
        save_button = v.findViewById(R.id.save_button);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        image = v.findViewById(R.id.profile_image);


        edit_hobbies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_hobbies_button_click();
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              user_update_save_function();

            }
        });

        user_name.setText(sharedPreferences.getString("name","error"));
        user_email.setText(sharedPreferences.getString("email","error"));



        sharedPreferences.getString("username","nothing found");
       String profile_pic_url = sharedPreferences.getString("imageurl","none");
       if(profile_pic_url.equals("none"))
           profile_pic_url = sharedPreferences.getString("imageurl","none");

        if(!profile_pic_url.equals("none"))
        {
            Glide.with(this).load(profile_pic_url).into(image);
        }






            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    select_image();
//
//                    upload_image.setAlpha(1);
//                    upload_image.setEnabled(true);

                }
            });

//
//        upload_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                upload_user_image();
//            }
//        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    String key = snapshot.getKey();
                assert key != null;
                if (key.equals(fAuth.getCurrentUser().getUid())){
                    User info = snapshot.getValue(User.class);
                    user_name.setText(info.getName());

                    user_email.setText(info.getEmail());
                    }
            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        return v;





    }

    private void select_image() {

        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);

        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
            Log.d("imageUri", imageUri.toString());
            image.setImageURI(imageUri);
            upload_user_image();
        }
        else{
//            Toast.makeText(getContext(), "cannot find image", Toast.LENGTH_SHORT).show();
        }

    }

    private String getFileExtension(Uri uri){

        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    private void upload_user_image() {
//        upload_image.setEnabled(false);
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("uploading");
        pd.show();

        if(imageUri!=null){
           final StorageReference fileReference = mStorageRef.child(fAuth.getCurrentUser().getUid()).child(fAuth.getCurrentUser().getUid()+"."+getFileExtension(imageUri));
            fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    pd.setProgress(0);
                                }
                            },5000);

                            Toast.makeText(getContext(),"upload successful", Toast.LENGTH_LONG).show();

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    mDatabaseRef.child("users").child(fAuth.getCurrentUser().getUid()).child("imageurl").setValue(uri.toString());

                                    Log.d("image URL ", uri.toString());
                                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("userdetails", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();

                                    editor.putString("imageurl",uri.toString());
                                    editor.commit();



                                }
                            });

                              pd.dismiss();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            pd.setProgress((int)progress);
                        }
                    });

//            upload_image.setAlpha(0);

        }

        else{
            Toast.makeText(getContext(), "no file selected",Toast.LENGTH_SHORT).show();
            pd.dismiss();
//            upload_image.setAlpha(0);
        }



    }

    private void user_update_save_function() {


        update.getReference().child("users").child(fAuth.getCurrentUser().getUid()).child("name").setValue(user_name.getText().toString());
        update.getReference().child("users").child(fAuth.getCurrentUser().getUid()).child("email").setValue(user_email.getText().toString());
        Toast.makeText(getContext(),"Changes Saved",Toast.LENGTH_SHORT).show();
    }


    private void edit_hobbies_button_click() {

        Intent intent = new Intent(getActivity(), Initial_Registration.class);
        intent.putExtra("activity","ProfileFragment");
        startActivity(intent);

    }
}
