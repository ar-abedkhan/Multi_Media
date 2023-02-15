package com.abedkhan.multimedia.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abedkhan.multimedia.Activities.ContainerActivity;
import com.abedkhan.multimedia.Activities.MainActivity;
import com.abedkhan.multimedia.Activities.ShoppingMainActivity;
import com.abedkhan.multimedia.Model.UserModel;
import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.FragmentProfileBinding;
import com.bumptech.glide.Glide;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ProfileFragment extends Fragment {
    public ProfileFragment() {
    }

    private FragmentProfileBinding binding;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    List<UserModel>userModelList;
    String currentUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentProfileBinding.inflate(getLayoutInflater(),container,false);

        //        user profile clicked
        floatingButtonClicked();

//        binding.settings.setOnClickListener(view -> {
//            Intent intent = new Intent(requireContext(), ContainerActivity.class);
//            intent.putExtra("currentUser", currentUser);
//            startActivity(intent);
//        });




        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser!=null){
            currentUser=firebaseUser.getUid();
            databaseReference=FirebaseDatabase.getInstance().getReference("User").child(currentUser);

            Log.i("tag", "onCreate: "+currentUser);

        }

         databaseReference.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {

                 UserModel userModel=snapshot.getValue(UserModel.class);

                 if (userModel!=null){

                     binding.userProfileName.setText(userModel.getFullName().trim());
//                  binding.userJoinedDate.setText((int) userModel.getIdCreationTimeMillis());
                  binding.userProfession.setText(userModel.getProfession().trim());
                  binding.userCountry.setText(userModel.getLivingCountry().trim());
                  binding.userLiveIn.setText(userModel.getLivingCity().trim());
                  binding.userGender.setText(userModel.getGender().trim());
                  binding.userName.setText(userModel.getUserName().trim());
                  binding.userMail.setText(userModel.getEmail().trim());
                  binding.userProfileBio.setText(userModel.getUserBio().trim());
                  binding.userDateofBirth.setText(userModel.getDateOfBirth().trim());

                  Glide.with(requireContext()).load(userModel.getProfileImgUrl()).into(binding.userProfileImg);

                     Log.i("tag", "onCreate: "+userModel.getFullName());


                 }

             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });






        return binding.getRoot();
    }

    private void floatingButtonClicked() {

        binding.settings.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), ContainerActivity.class);
            intent.putExtra("settingsClicked", true);
            intent.putExtra("currentUser", currentUser);
            startActivity(intent);
        });

        binding.message.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), ContainerActivity.class);
            intent.putExtra("isMessageClicked", true);
            startActivity(intent);
        });

        binding.addPost.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), MainActivity.class);
            intent.putExtra("isAddClicked", true);
            startActivity(intent);
        });

        binding.logeOutBtn.setOnClickListener(view -> {
            firebaseAuth.signOut();
            startActivity(new Intent(requireContext(),MainActivity.class));

        });

    }
}