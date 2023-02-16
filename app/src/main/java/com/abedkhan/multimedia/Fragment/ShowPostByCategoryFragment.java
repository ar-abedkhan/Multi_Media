package com.abedkhan.multimedia.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abedkhan.multimedia.Adapters.CategoryAdapter;
import com.abedkhan.multimedia.Listeners.CategoryListener;
import com.abedkhan.multimedia.Model.PostModel;
import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.SavedData.CategorySavedData;
import com.abedkhan.multimedia.databinding.FragmentShowPostByCategoryBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ShowPostByCategoryFragment extends Fragment implements CategoryListener {

    public ShowPostByCategoryFragment() {
        // Required empty public constructor
    }

    FragmentShowPostByCategoryBinding binding;
    List<PostModel> postList;
    DatabaseReference databaseReference;
    List<String> categoryList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentShowPostByCategoryBinding.inflate(getLayoutInflater(), container, false);
//        categoryList = new ArrayList<>();
        postList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        CategorySavedData.loadCategory();
        categoryList = CategorySavedData.getCategorySavedData();
//        Log.i("TAG", "categoryFragment "+ categoryList.size());

//        for (int i =0; i< categoryList.size(); i++) {
//            Log.i("TAG", "categoryName: "+categoryList.get(i));
//        }
        CategoryAdapter adapter = new CategoryAdapter(getContext(), categoryList, this);
        binding.horizontalRecycler.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void selectedCategory(String category) {

    }
}