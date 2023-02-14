package com.abedkhan.multimedia.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abedkhan.multimedia.Model.PostModel;
import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.FragmentHomeSubContainerBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeSubContainerFragment extends Fragment {

    public HomeSubContainerFragment() {
        // Required empty public constructor
    }

    FragmentHomeSubContainerBinding binding;

    int pagePosition=0;
    static List<PostModel> postList;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentHomeSubContainerBinding.inflate(getLayoutInflater(),container,false);
        postList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (getArguments() != null) {
            pagePosition = getArguments().getInt("position");
//            Log.i("TAG", "Position(Home sub container): "+pagePosition);
            String p=String.valueOf(pagePosition);
            binding.text.setText(p);
        }

        if (pagePosition== 0){
//            here we will setup the home page
//            getPostDataFromCloud("");
        }
        else if (pagePosition== 1) {
//            category setting
//            getPostDataFromCloud("horror");
        }
        else if (pagePosition== 2) {
//            popular post settings
        }
        else if (pagePosition== 3) {
//            popular writer setting
        }

        return binding.getRoot();
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

                        Log.i("TAG", "getting data in home--: ");
//                        TODO: setting data to the view
                    }
                    else {
                        getDataByCategory(categoryName, num, postList);
                        num++;
                        Log.i("TAG", "getting data in category ");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void getDataByCategory(String categoryName, int postNumber, List<PostModel> postList) {
        Log.i("TAG", "Post title (in category): "+ postList.get(postNumber).getTitle());
        List<PostModel> postModelList = new ArrayList<>();
        databaseReference.child("Category").child(postList.get(postNumber).getPostID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("TAG", "Category: inside snapshot");
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Log.i("TAG", "Category: inside snapshot2");

                    Map<Object, Object> category = (Map<Object, Object>) dataSnapshot.getValue();
//                    TODO: line 120 e error bolche... ei line er porer log gula show o hocche nah
                    Log.i("TAG", "Category: getting data from snapshot");

                    int size = (int) category.get("categorySize");
                    for (int n = 1; n<= size; n++) {
                        String catName = (String) category.get(String.valueOf(n));
                        if (catName.equals(categoryName)){
                            postModelList.add(postList.get(postNumber));
                        }
                        Log.i("TAG", "Category: "+ postModelList.get(postNumber).getPostID());
                    }

                    Log.i("TAG", "Category: "+ postModelList.get(postNumber).getTitle());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Log.i("TAG", "Category cancel error "+error.toException().getLocalizedMessage());
            }
        });
    }

}