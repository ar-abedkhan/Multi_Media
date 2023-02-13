package com.abedkhan.multimedia.Fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abedkhan.multimedia.Activities.MainActivity;
import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.FragmentAddPostBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AddPostFragment extends Fragment {
    public AddPostFragment() {
        // Required empty public constructor
    }


    FragmentAddPostBinding binding;
    String title, mainText, category, postImgUrl;
    Uri postImgUri;

    StorageReference storageReference;
    ArrayList<String> categoryList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddPostBinding.inflate(getLayoutInflater(), container, false);
        categoryList = new ArrayList<>();

        storageReference = FirebaseStorage.getInstance().getReference("PostImg");


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

//        Handling category save button
        binding.addCategoryBtn.setOnClickListener(view -> {
            category = binding.category.getText().toString();
            categoryList.add(category);

            binding.addedCategoryViewer.append(category+", ");
        });

//        Handling save button
        binding.saveBtn.setOnClickListener(view -> {
            saveOrPublish("save");
        });

//                Handling save button
        binding.saveBtn.setOnClickListener(view -> {
            saveOrPublish("publish");
        });

//        Handling back button
        binding.backBtn.setOnClickListener(view -> {
            getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
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
        title = binding.title.getText().toString();
        mainText = binding.mainText.getText().toString();

    }
}