package com.abedkhan.multimedia.Fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.abedkhan.multimedia.Activities.MainActivity;
import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.SavedData.CategorySavedData;
import com.abedkhan.multimedia.databinding.FragmentAddPostBinding;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddPostFragment extends Fragment {
    public AddPostFragment() {
        // Required empty public constructor
    }


    FragmentAddPostBinding binding;
    String title="", mainText, category, postImgUrl= "", draftOrPublished;
    String postID, ownerID;
    long postTimeMillis;
    Uri postImgUri;

    StorageReference storageReference;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    ArrayList<String> categoryList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddPostBinding.inflate(getLayoutInflater(), container, false);
        categoryList = new ArrayList<>();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        ownerID = firebaseUser.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference("PostImg");

//        *Setting data to the auto complete text view
        CategorySavedData.loadCategory();
        List<String> categoryDataList = CategorySavedData.getCategorySavedData();

        ArrayAdapter<String> catAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, categoryDataList);

        binding.category.setAdapter(catAdapter);


//        Handling on click button
        binding.addImgBtn.setOnClickListener(view -> {
//            !getting image from the device
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, 102);
        });

        binding.postImg.setOnClickListener(view -> {
//            !getting image from the device
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, 102);
        });

//        Handling Add category option
        binding.addCategoryOption.setOnClickListener(view -> {
            binding.addCategoryOption.setVisibility(View.GONE);
            binding.addCategoryContainer.setVisibility(View.VISIBLE);
            binding.addedCategoryViewer.setVisibility(View.VISIBLE);
        });

//        Handling category save button


            binding.addCategoryBtn.setOnClickListener(view -> {
                if (!binding.category.equals("")){

                    category = binding.category.getText().toString();
                    categoryList.add(category);

                    binding.addedCategoryViewer.append(category+", ");
                    binding.category.setText("");

                }else {
                    binding.category.setError("Category filed required");
                }

            });



//        Handling save button
        binding.saveBtn.setOnClickListener(view -> {
//            Log.i("TAG", "saveBtnClicked ");

            binding.saveBtn.setEnabled(false);
            binding.publishBtn.setEnabled(false);
            binding.backBtn.setEnabled(false);
            binding.progressbar.setVisibility(View.VISIBLE);

            if (binding.mainText.getText().toString().isEmpty()){
                binding.mainText.setError("Field cannot be empty!");
            }else {
                saveOrPublish("save");
            }
        });

//                Handling publish button
        binding.publishBtn.setOnClickListener(view -> {
//            Log.i("TAG", "publishBtn clicked ");
            binding.saveBtn.setEnabled(false);
            binding.publishBtn.setEnabled(false);
            binding.backBtn.setEnabled(false);
            binding.progressbar.setVisibility(View.VISIBLE);

            if (binding.mainText.getText().toString().isEmpty()){
                binding.mainText.setError("Field cannot be empty!");
            }else {
                saveOrPublish("publish");
            }
        });

//        Handling back button
        binding.backBtn.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        });


        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 102 && resultCode == RESULT_OK && data != null){
            postImgUri = data.getData();

//            if the process is successful than add img button will hide
            binding.addImgBtnCon.setVisibility(View.GONE);
            binding.postImgCon.setVisibility(View.VISIBLE);
            binding.postImg.setImageURI(postImgUri);
        }

    }

    private void saveOrPublish(String allOperation) {
//        if allOperation is publish it will publish the post
        title = binding.title.getText().toString().trim();
        mainText = binding.mainText.getText().toString().trim();

        postID = databaseReference.push().getKey();
//        Log.i("TAG", "saveOrPublish1: "+ postID);

        if (postImgUri != null){
            storageReference.child(postID).putFile(postImgUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()){
//                        Getting the image Url from the cloud storage
                        storageReference.child(postID).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                postImgUrl = String.valueOf(uri);
                                Glide.with(getActivity()).load(postImgUrl).placeholder(R.drawable.lightning_tree).into(binding.postImg);
                                savingDataToDatabase(postID, ownerID, title, mainText, postImgUrl, allOperation);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i("TAG", "image failed: "+ e.getLocalizedMessage());
                            }
                        });
                    }
                    else{
//                        handling image storing failed situation
                        binding.saveBtn.setEnabled(true);
                        binding.publishBtn.setEnabled(true);
                        binding.backBtn.setEnabled(true);
                        binding.progressbar.setVisibility(View.GONE);
                    }
                }
            });
        }
        else {
            postImgUrl = "";
            savingDataToDatabase(postID, ownerID, title, mainText, postImgUrl, allOperation);
        }

    }

    private void savingDataToDatabase(String postID, String ownerID, String title, String mainText, String postImgUrl, String allOperation) {
        /*
        * Saving process will be completed in three steps
        * 1. save image in the cloud and then send an Url of the image
        * 2. get img Url or give the value of url= "" and start this function
        * 3. in this function, after saving the basic data we will try to save the category data in a different data table
        * */

        long postTimeMillis = System.currentTimeMillis();

        Map<String, Object> postMap = new HashMap<>();
        postMap.put("postID", postID);
        postMap.put("ownerID", ownerID);
        postMap.put("title", title);
        postMap.put("mainText", mainText);
        postMap.put("postImgUrl", postImgUrl);
        postMap.put("allOperation", allOperation);
        postMap.put("postTimeMillis", postTimeMillis);
        postMap.put("postLike", 0);
        postMap.put("postComment", 0);

//        saving the main data to firebase (without category list)
        databaseReference.child("Post").child(postID).setValue(postMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
//                after successfully saving main data, trying to save category
                Map<String, Object> catMap = new HashMap<>();

                if (!categoryList.isEmpty()){
                    int num= 1;

                    catMap.put("categorySize", categoryList.size());
                    for (String category:categoryList) {
                        catMap.put(String.valueOf(num), category);
//                        catMap.put(category, category);
                        num++;
                    }
                }
                else {
                    catMap.put("categorySize", 0);
                }

//                saving category list to category data table in the cloud
                databaseReference.child("Category").child(postID).setValue(catMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getActivity(), "Task saved successfully!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Log.i("TAG", "failed on category: "+ task.getException().getLocalizedMessage());
                            Toast.makeText(getActivity(), ""+task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                        getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("TAG", "Failed on main data: "+ e.getLocalizedMessage());
            }
        });
    }
}