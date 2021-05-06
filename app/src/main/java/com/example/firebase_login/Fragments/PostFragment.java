package com.example.firebase_login.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firebase_login.Activities.PostActivity;
import com.example.firebase_login.R;
import com.google.firebase.storage.StorageReference;

public class PostFragment extends Fragment {


    Uri imageUri;
    String myUri = "";
    StorageReference storageReference;

    ImageView close, image_added;
    TextView post;
    EditText description;


    public PostFragment() {
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.home_fragment_menu,menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_post, container, false);
//
//        close = v.findViewById(R.id.close);
//        image_added = v.findViewById(R.id.image_added);
//        post = v.findViewById(R.id.post);
//        description = v.findViewById(R.id.description);
//
//        storageReference = FirebaseStorage.getInstance().getReference("posts");
//
//        close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), MainActivity.class);
//                startActivity(intent);
//                getActivity().finish();
//            }
//        });
//
//        post.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                uploadImage();
//            }
//        });
//        CropImage.activity()
//                .setAspectRatio(1,1)
//                .start(getActivity());

        Intent intent = new Intent(getActivity(), PostActivity.class);
        startActivity(intent);
        getActivity().finish();

        return v;
    }

//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
//
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//
//            imageUri = result.getUri();
//
//            image_added.setImageURI(imageUri);
//
//        }
//        else{
//
//            Toast.makeText(getActivity(),"Something went wrong",Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(getActivity(), MainActivity.class);
//            startActivity(intent);
//            getActivity().finish();
//
//        }
//
//
//    }
//
//    private String getFileExtension(Uri uri){
//
//        ContentResolver contentResolver = getActivity().getContentResolver();
//        MimeTypeMap mime = MimeTypeMap.getSingleton();
//        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
//
//    }
//
//
//    private void uploadImage() {
//        ProgressDialog pd = new ProgressDialog(getContext());
//        pd.setMessage("Posting");
//        pd.show();
//        pd.setCancelable(false);
//
//        if(imageUri!=null){
//
//            final StorageReference fileRefrence = storageReference.child(System.currentTimeMillis()
//            +"."+getFileExtension(imageUri));
//
//            UploadTask uploadTask = fileRefrence.putFile(imageUri);
//            uploadTask.continueWithTask(new Continuation() {
//                @Override
//                public Object then(@NonNull  Task task) throws Exception {
//                    if (!task.isSuccessful()){
//                        throw task.getException();
//                    }
//
//                    return fileRefrence.getDownloadUrl();
//                }
//            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                @Override
//                public void onComplete(@NonNull Task<Uri> task) {
//
//                    if(task.isSuccessful()){
//                        Uri downloadUri = task.getResult();
//                        myUri = downloadUri.toString();
//
//                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("posts");
//
//                        String postid = reference.push().getKey();
//
//                        HashMap<String,Object> hashMap = new HashMap<>();
//                        hashMap.put("postid", postid);
//                        hashMap.put("postimage", myUri);
//                        hashMap.put("description", description.getText().toString());
//                        hashMap.put("postimage", myUri);
//                        hashMap.put("publisher", FirebaseAuth.getInstance().getCurrentUser().getUid());
//
//                        reference.child(postid).setValue(hashMap);
//
//                        Intent intent = new Intent(getActivity(), MainActivity.class);
//                        startActivity(intent);
//                        getActivity().finish();
//
//                    }
//                    else {
//                        Toast.makeText(getContext(),"Failed!",Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(getContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
//
//                }
//            });
//        }
//        else {
//            Toast.makeText(getContext(),"No Image selected!",Toast.LENGTH_SHORT).show();
//
//        }
//
//
//
//
//
//
//    }


}