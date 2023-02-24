package com.abedkhan.multimedia.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abedkhan.multimedia.Adapters.PostAdapter;
import com.abedkhan.multimedia.Listeners.PostListener;
import com.abedkhan.multimedia.Model.FollowerFollowingModel;
import com.abedkhan.multimedia.Model.PostModel;
import com.abedkhan.multimedia.Model.UserModel;
import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.Extras.CategorySavedData;
import com.abedkhan.multimedia.databinding.FragmentHomeSubContainerBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeSubContainerFragment extends Fragment implements PostListener {

    public HomeSubContainerFragment() {
        // Required empty public constructor
    }

    FragmentHomeSubContainerBinding binding;

    int pagePosition=0;
    List<PostModel> postList;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    String currentUserID, currentUserName, currentUserImg;
   int comments,likes;
    String visitedUserID, visitedUserProfileImg, visitedUserName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentHomeSubContainerBinding.inflate(getLayoutInflater(),container,false);
        postList = new ArrayList<>();


        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        binding.progressbar.setVisibility(View.VISIBLE);
    binding.shimmerpostRecycler.startShimmer();
        if (firebaseUser!=null){
            currentUserID =firebaseUser.getUid();
        }

//        ##Getting current user data
        databaseReference.child("User").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Log.i("TAG", "Profile fragment 2 ");
                UserModel userModel=snapshot.getValue(UserModel.class);

                if (userModel!=null){
                    currentUserName = userModel.getFullName();
                    currentUserImg = userModel.getProfileImgUrl();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        if (getArguments() != null) {
            pagePosition = getArguments().getInt("position");
//            Log.i("TAG", "Position(Home sub container): "+pagePosition);
            String p=String.valueOf(pagePosition);
//            binding.text.setText(p);

//            --trying to avoid error

            if (pagePosition== 0){
//            here we will setup the home page
                getPostDataFromCloud("");
            }
//            else if (pagePosition== 1) {
////            category setting
////            getPostDataFromCloud("test");
//                testFunction();
//            }
            else if (pagePosition== 2) {
//            popular post settings
            }
            else if (pagePosition== 3) {
//            popular writer setting
            }

        }


        return binding.getRoot();
    }

    private void testFunction() {
        Log.i("TAG", "Home extra Fragment");
        List<String> list = new ArrayList<>();
        list = CategorySavedData.getCategorySavedData();

        for (int i =0; i< list.size(); i++) {
            Log.i("TAG", "categoryName: "+list.get(i));
        }
    }

    private void getPostDataFromCloud(String categoryName) {
        /*
        * if the value of categoryName is empty, that means it is from the home tab
        * if the value of categoryName is not empty that means it is from the category tab
        *   - in that case we will send the values to the getDataByCategory function and after that we will show the data
        * */

//        Getting post list from cloud

        databaseReference.child("Post").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int num = 0;

                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    PostModel model = dataSnapshot.getValue(PostModel.class);

                    likes=model.getPostLike();
                    comments=model.getPostComment();

                    postList.add(model);
                    if (categoryName.equals("")) {
                        setDataToView();
//                        Log.i("TAG", "getting data in home--: ");
//                        TODO: setting data to the view
                    }
                    else {
                        getDataByCategory(categoryName, num, postList);
                        num++;
//                        Log.i("TAG", "getting data in category ");
                    }
                }
//                binding.progressbar.setVisibility(View.GONE);
         binding.shimmerpostRecycler.stopShimmer();
            binding.postRecycler.setVisibility(View.VISIBLE);}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });



    }

    private void setDataToView() {
        PostAdapter adapter = new PostAdapter(getContext(), postList,this);
        binding.postRecycler.setAdapter(adapter);
    }

    private void getDataByCategory(String categoryName, int postNumber, List<PostModel> postList) {
        List<PostModel> postModelList = new ArrayList<>();
        databaseReference.child("Category").child(postList.get(postNumber).getPostID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
//                    Log.i("TAG", "Category: inside snapshot2");

                    /*
                    * Here, categoryValue only returning the value of the map
                    * Even though we don't need the if statement, I am using it in case it may be helpful in the future
                    * */

                    Object categoryValue = dataSnapshot.getValue();
                    if (categoryValue instanceof Map) {
                        Map<Object, Object> category = (Map<Object, Object>) categoryValue;

//                        Log.i("TAG", "Category: getting data from snapshot");

    //                    int size = (int) category.get("categorySize");
    //                    for (int n = 1; n<= size; n++) {
    //                        String catName = (String) category.get(String.valueOf(n));
    //                        if (catName.equals(categoryName)){
    //                            postModelList.add(postList.get(postNumber));
    //                        }
    //                        Log.i("TAG", "Category: "+ postModelList.get(postNumber).getPostID());
    //                    }

                        String catName = (String) category.get(categoryName);
                        if (catName.equals(categoryName)){
                            postModelList.add(postList.get(postNumber));
                        }
                        Log.i("TAG", "Category: "+ postModelList.get(postNumber).getPostID());

                        Log.i("TAG", "Category: "+ postModelList.get(0).getTitle());
                    }

                    else {
//                        Log.i("TAG", "data snapshot else value: "+ categoryValue);

                        if (categoryValue.equals(categoryName)){
                            postModelList.add(postList.get(postNumber));
                            Log.i("TAG", "Category===: "+ postModelList.get(0).getPostID());
//                            TODO: place adapter

                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

//        Log.i("TAG", "Home Sub Container Fragment...");
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
                        public void onComplete(@NonNull Task<Void> task) {
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
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
        return finalFollowing;

    }

    boolean isFollowing = false;
    private boolean isFollowing(String visitedUserID) {
        databaseReference.child("Following").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
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
            public void onCancelled(@NonNull DatabaseError error) {
                isFollowing = false;

            }
        });
        return isFollowing;
    }
}