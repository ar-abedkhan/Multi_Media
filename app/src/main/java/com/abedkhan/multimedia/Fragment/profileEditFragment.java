package com.abedkhan.multimedia.Fragment;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.abedkhan.multimedia.Model.UserModel;
import com.abedkhan.multimedia.R;
import com.abedkhan.multimedia.databinding.FragmentProfileEditBinding;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


public class profileEditFragment extends Fragment {


    public profileEditFragment() {
    }
    FragmentProfileEditBinding binding;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    StorageReference storageReference;
    FirebaseAuth firebaseAuth;
    String currentUserId;
    Intent intent;
    Uri profileUri;
    String profileUrl;
    String userName,userMail,userFullName,profession,country,city,birthDate,bio,pass;
    ProgressDialog dialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentProfileEditBinding.inflate(getLayoutInflater(),container,false);

//        handling back button
        binding.backBtn.setOnClickListener(view -> {
            Fragment fragment = new ProfileFragment();

            FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.containerFrame, fragment);
            transaction.commit();
        });







        dialog=new ProgressDialog(requireContext());
        dialog.setTitle("Save changes..");
        dialog.setMessage("Please Wait");







        intent=getActivity().getIntent();
        currentUserId=intent.getStringExtra("currentUser");
//        databaseReference= FirebaseDatabase.getInstance().getReference("User").child(currentUserId);
//        storageReference= FirebaseStorage.getInstance().getReference("ProfileImg").child(currentUserId);

binding.userProfileImg.setOnClickListener(view -> {
    Intent intent=new Intent();
    intent.setAction(Intent.ACTION_GET_CONTENT);
    intent.setType("image/*");
    startActivityForResult(intent,101);
    Toast.makeText(requireContext(), "chose image", Toast.LENGTH_SHORT).show();

});


           if (currentUserId!=null){

               databaseReference= FirebaseDatabase.getInstance().getReference("User").child(currentUserId);
               storageReference= FirebaseStorage.getInstance().getReference("ProfileImg").child(currentUserId);

               databaseReference.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {

                       UserModel userModel=snapshot.getValue(UserModel.class);

                       if (!userModel.equals("")){
                           profileUrl=userModel.getProfileImgUrl();
                           binding.userProfileName.setText(userModel.getFullName().trim());
                           binding.userProfession.setText(userModel.getProfession().trim());

                           binding.userProfileBio.setText(userModel.getUserBio().trim());
                           binding.userCountry.setText(userModel.getLivingCountry().trim());
                           binding.userLiveIn.setText(userModel.getLivingCity().trim());
                           binding.userName.setText(userModel.getUserName().trim());
                           binding.userMail.setText(userModel.getEmail().trim());
                           binding.password.setText(userModel.getPassword().trim());

                           //requercontex cilo get contex e baloi kj kore.....
                           try {

                               Glide.with(getActivity().getApplicationContext()).load(userModel.getProfileImgUrl()).placeholder(R.drawable.ic_baseline_person_24).into(binding.userProfileImg);

                           }catch (Exception exception){

                           }

                           Log.i("tag", "onCreate: "+userModel.getFullName());
                       }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });

           }


           binding.saveChangesDataBtn.setOnClickListener(view -> {

dialog.show();
binding.saveChangesDataBtn.setEnabled(false);

     userFullName=binding.userProfileName.getText().toString();
     userName=binding.userName.getText().toString();
     userMail=binding.userMail.getText().toString();
     bio=binding.userProfileBio.getText().toString();
     profession=binding.userProfession.getText().toString();
     birthDate=getDOBFromView();
     country=binding.userCountry.getText().toString();
     city=binding.userLiveIn.getText().toString();
     pass=binding.password.getText().toString();


               if (profileUri == null){

                   if (userName.equals("")){
                       binding.userName.setError("Username field cant Empty");
                   }else if (userMail.equals("")) {
                       binding.userMail.setError("Mail field cant Empty");
                   }else if (userFullName.equals("")){
                       binding.userProfileName.setError("Name field cant Empty");
                   }else if (birthDate.equals("")) {

                   }else{

                       Map<String, Object> userMap = new HashMap<>();
                       userMap.put("userID", currentUserId);
                       userMap.put("password", pass);
                       userMap.put("profileImgUrl", profileUrl);
                       userMap.put("fullName", userFullName);
                       userMap.put("userName", userName);
                       userMap.put("email", userMail);
                       userMap.put("dateOfBirth", birthDate);
                       userMap.put("userBio", bio);
                       userMap.put("profession", profession);
                       userMap.put("livingCountry", country);
                       userMap.put("livingCity", city);

                       Log.i("tag", "hasmap " + userMap);


                       databaseReference.updateChildren(userMap).addOnCompleteListener(task -> {
                           if (task.isSuccessful()){
                               Toast.makeText(getContext(), "Profile Information saved successfully❤", Toast.LENGTH_LONG).show();
                               dialog.dismiss();

                               Fragment fragment = new ProfileFragment();

                               FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                                       .beginTransaction()
                                       .replace(R.id.containerFrame, fragment);
                               transaction.commit();

                           }
                           else {
//                                   showAlert("Error", task.getException().getLocalizedMessage());
//                               dialog.show();
                           }
//                           binding.saveChangesDataBtn.setVisibility(View.VISIBLE);
//                           binding.saveChangesDataBtn.setVisibility(View.GONE);
                           binding.saveChangesDataBtn.setEnabled(true);

                       });

                   }

               }else {

                   ProfileImgSetup();

               }
           });















        return binding.getRoot();
    }

    private void ProfileImgSetup() {

        storageReference= FirebaseStorage.getInstance().getReference("ProfileImg").child(currentUserId);

        storageReference.child("ProfileImg").putFile(profileUri).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Log.i("tag", "putfile for profile: ");

                storageReference.child("ProfileImg").getDownloadUrl().addOnSuccessListener(uri -> {
                    Log.i("tag", "download url for profile: ");

                    profileUrl =String.valueOf(uri);
                    Log.i("tag", "download url for profile: "+profileUrl);

                    dataUpdate();
                    dialog.dismiss();
                    binding.saveChangesDataBtn.setEnabled(true);
                }).addOnFailureListener(e -> {

                    Toast.makeText(requireContext(), "Image load failed", Toast.LENGTH_SHORT).show();

                });
            }
        });
    }

    private void dataUpdate() {

        if (profileUrl !=null) {

            if (userFullName.isEmpty()) {
                binding.userProfileName.setError("Name field cant Empty");

            }else if (userName.isEmpty()){
                binding.userName.setError("username field cant Empty");

            }else if (userMail.isEmpty()) {
                    binding.userMail.setError("Mail field cant Empty");
            }else {

                    Map<String, Object> userMap = new HashMap<>();
                    userMap.put("userID", currentUserId);
                    userMap.put("password", pass);
                    userMap.put("profileImgUrl", profileUrl);
                    userMap.put("fullName", userFullName);
                    userMap.put("userName", userName);
                    userMap.put("email", userMail);
                    userMap.put("dateOfBirth", birthDate);
                    userMap.put("userBio", bio);
                    userMap.put("profession", profession);
                    userMap.put("livingCountry", country);
                    userMap.put("livingCity", city);

                    Log.i("tag", "hasmap " + userMap);

//                    databaseReference.child("User").child(currentUserId).setValue(userMap).addOnCompleteListener(task -> {
                databaseReference.updateChildren(userMap).addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {

//                            Log.i("tag", "data update ");
                            try {
                                Toast.makeText(requireContext(), "Profile image saved", Toast.LENGTH_SHORT).show();
                            }catch (Exception exception){}

                            dialog.dismiss();
                            binding.saveChangesDataBtn.setEnabled(true);

                        } else {
                            Toast.makeText(requireContext(), "Profile image Upload failed", Toast.LENGTH_SHORT).show();
                        }

                    });
                }
            }
        }


//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && data != null){
            profileUri = data.getData();
            binding.userProfileImg.setImageURI(profileUri);
            Toast.makeText(requireContext(), "show image", Toast.LENGTH_SHORT).show();
            Log.i("tag", "img show: "+profileUri);
        }

    }
    private String getDOBFromView() {
        int date = binding.agePicker.getDayOfMonth();
        int month = binding.agePicker.getMonth()+1;
        int year = binding.agePicker.getYear();
        return  date+"/"+month+"/"+year;
    }


//    private void status(String status){
//        databaseReference= FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());
//
//        HashMap<String , Object> hashMap=new HashMap<>();
//        hashMap.put("status",status);
//        databaseReference.updateChildren(hashMap);
//
//
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        status("online");
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        status("offline");
//    }

}