package com.abedkhan.multimedia.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abedkhan.multimedia.Adapters.CategoryAdapter;
import com.abedkhan.multimedia.Adapters.PostAdapter;
import com.abedkhan.multimedia.Listeners.CategoryListener;
import com.abedkhan.multimedia.Listeners.PostListener;
import com.abedkhan.multimedia.Model.PostModel;
import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.SavedData.CategorySavedData;
import com.abedkhan.multimedia.databinding.FragmentShowPostByCategoryBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShowPostByCategoryFragment extends Fragment implements CategoryListener, PostListener {

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
        CategoryAdapter adapter = new CategoryAdapter(getContext(), categoryList, this);
        binding.horizontalRecycler.setAdapter(adapter);

        getPostDataFromCloud("");
        setDataToView(postList);

        return binding.getRoot();
    }

    @Override
    public void selectedCategory(String category) {
        getPostDataFromCloud(category);
    }

    private void getPostDataFromCloud(String categoryName) {
        /*
         * if the value of categoryName is empty, that means it is from the home tab
         * if the value of categoryName is not empty that means it is from the category tab
         *   - in that case we will send the values to the getDataByCategory function and after that we will show the data
         * */

        databaseReference.child("Post").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int num = 0;

                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    PostModel model = dataSnapshot.getValue(PostModel.class);

                    postList.add(model);
                    if (categoryName.equals("")) {
                        setDataToView(postList);
                    }
                    else {
//                        After getting all post now we will search post by category
                        getDataByCategory(categoryName, num, postList);
                        num++;
                    }
                }
//                binding.progressbar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });



    }


    private void setDataToView(List<PostModel> postModelList) {
        PostAdapter adapter = new PostAdapter(getContext(), postModelList, this);
        binding.postRecycler.setAdapter(adapter);
    }

    private void getDataByCategory(String categoryName, int postNumber, List<PostModel> postList) {
        List<PostModel> postModelList = new ArrayList<>(); // This list contains all the post list that are filtered by category
        databaseReference.child("Category").child(postList.get(postNumber).getPostID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@androidx.annotation.NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    /*
                     * Here, categoryValue only returning the value of the map
                     * Even though we don't need the if statement, I am using it in case it may be helpful in the future
                     * */
                    Object categoryValue = dataSnapshot.getValue();
                    if (categoryValue instanceof Map) {
                        Map<Object, Object> category = (Map<Object, Object>) categoryValue;

                        String catName = (String) category.get(categoryName);
                        if (catName.equals(categoryName)){
                            postModelList.add(postList.get(postNumber));
                        }
                        Log.i("TAG", "Category: "+ postModelList.get(postNumber).getPostID());

                        Log.i("TAG", "Category: "+ postModelList.get(0).getTitle());
                    }

                    else {

                        if (categoryValue.equals(categoryName)){
//                            passing all filtered data and showing them to the recycler
                            postModelList.add(postList.get(postNumber));
                            setDataToView(postModelList);

                        }
                    }

                }
//                setDataToView(postModelList);
            }

            @Override
            public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {

                Log.i("TAG", "Category cancel error "+error.toException().getLocalizedMessage());
            }
        });
    }

//    Going to the Profile fragment
    @Override
    public void gotoFragmentWithValue(Fragment fragment, String userID) {
        Bundle bundle = new Bundle();
        bundle.putString("VisitedUserID", userID);
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}