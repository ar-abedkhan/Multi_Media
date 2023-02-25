package com.abedkhan.multimedia.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abedkhan.multimedia.Adapters.CategoryAdapter;
import com.abedkhan.multimedia.Adapters.PostAdapter;
import com.abedkhan.multimedia.Listeners.CategoryListener;
import com.abedkhan.multimedia.Listeners.PostListener;
import com.abedkhan.multimedia.Model.FollowerFollowingModel;
import com.abedkhan.multimedia.Model.PostModel;
import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.Extras.CategorySavedData;
import com.abedkhan.multimedia.databinding.FragmentShowPostByCategoryBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
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

    String currentUserID, currentUserName, currentUserImg;
    String visitedUserID, visitedUserProfileImg, visitedUserName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentShowPostByCategoryBinding.inflate(getLayoutInflater(), container, false);
//        categoryList = new ArrayList<>();
        postList = new ArrayList<>();

        binding.shimmerpostRecycler.startShimmer();

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
                binding.shimmerpostRecycler.stopShimmer();
                binding.shimmerpostRecycler.setVisibility(View.GONE);
                binding.postRecycler.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });



    }


    private void setDataToView(List<PostModel> postModelList) {
        PostAdapter adapter = new PostAdapter(getContext(), postModelList,this);
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

    //    Handling follow button
    boolean finalFollowing = false;
    @Override
    public boolean followButtonClickedEvent(String visitedUserID) {
        this.visitedUserID = visitedUserID;
        boolean isFollowing = isFollowing(visitedUserID);
        if(!currentUserID.equals(visitedUserID) && !isFollowing){
            Map<String, Object> map = new HashMap<>();
            map.put("OwnProfileID", currentUserID);
            map.put("followerID", visitedUserID);
            map.put("followerName", visitedUserName);
            map.put("followProfileImg", visitedUserProfileImg);

//                Including the visited user to the following list
            databaseReference.child("Following").child(currentUserID).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
//                    binding.followText.setText("following");
                    finalFollowing = true;

                    /*
                     * This process will be completed in two steps
                     * 1. Firstly will check if the visiting user following the current user or not
                     * 2. If the visitor is following the user then isFollowing method will return false and the whole process will be cancelled else the process will continue
                     * 3. Lastly the user will be stored in the followed user's followers table
                     * */
                    Map<String, Object> map = new HashMap<>();
                    map.put("OwnProfileID", visitedUserID);
                    map.put("followerID", currentUserID);
                    map.put("followerName", currentUserName);
                    map.put("followProfileImg", currentUserImg);
                    databaseReference.child("Followers").child(visitedUserID).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@androidx.annotation.NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getContext(), "Following the user", Toast.LENGTH_SHORT).show();
                            }else{
                                Log.i("TAG", "Follower upload failed: "+ task.getException().getLocalizedMessage());
                            }
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@androidx.annotation.NonNull Exception e) {

                }
            });
        }
        return finalFollowing;

    }

    boolean isFollowing = false;
    private boolean isFollowing(String visitedUserID) {
        databaseReference.child("Following").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@androidx.annotation.NonNull DataSnapshot snapshot) {
                try {
                    for (DataSnapshot snap: snapshot.getChildren()) {
                        FollowerFollowingModel model = snap.getValue(FollowerFollowingModel.class);
                        if (visitedUserID.equals(model.getFollowerID())){
                            isFollowing = true;
                        }
                    }
                }catch (Exception e){
                    isFollowing = false;
                }

            }

            @Override
            public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {
                isFollowing = false;

            }
        });
        return isFollowing;
    }
}