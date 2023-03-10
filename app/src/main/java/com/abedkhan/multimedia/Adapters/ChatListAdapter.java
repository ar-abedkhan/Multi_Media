package com.abedkhan.multimedia.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.abedkhan.multimedia.Activities.ContainerActivity;
import com.abedkhan.multimedia.AllViewHolder.ChatListViewHolders;
import com.abedkhan.multimedia.Fragment.ChattingFragment;
import com.abedkhan.multimedia.Fragment.ProfileFragment;
import com.abedkhan.multimedia.Fragment.ReadStoryFragment;
import com.abedkhan.multimedia.Listeners.PostListener;
import com.abedkhan.multimedia.Model.ChatListModel;
import com.abedkhan.multimedia.Model.UserModel;
import com.abedkhan.multimedia.R;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListViewHolders> {
//    List<ChatListModel> chatListModelList;
    List<UserModel> userModelList;
    Context context;
    PostListener listener;
    boolean ischat;


    List<ChatListModel>chatListModelList=new ArrayList<>();
    String lastMess;


    public ChatListAdapter(List<UserModel> userModelList, Context context, PostListener listener, boolean ischat) {
        this.userModelList = userModelList;
        this.context = context;
        this.listener = listener;
        this.ischat = ischat;
    }

    @NonNull
    @Override
    public ChatListViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.chatrecyclerview,parent,false);
        return new ChatListViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListViewHolders holder, int position) {

//        ChatListModel chatListModel=chatListModelList.get(position);
        UserModel userModel=userModelList.get(position);

        holder.name.setText(userModel.getFullName());
//        holder.message.setText(chatListModel.getLastMessage());
//        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
//        Date date=new Date(chatListModel.getLastMessage());

//        holder.lastSeen.setText(simpleDateFormat.format(date));



        Glide.with(context).load(userModel.getProfileImgUrl()).placeholder(R.drawable.ic_baseline_person_24).into(holder.profileImg);


        holder.itemView.setOnClickListener(view -> {

            AppCompatActivity appCompatActivity= (AppCompatActivity) view.getContext();
            ChattingFragment chattingFragment=new ChattingFragment();
//            passing post data to the fragment
            Bundle bundle = new Bundle();
            bundle.putString("connectedUser", userModel.getUserID());
            chattingFragment.setArguments(bundle);


//            Intent intent = new Intent(context, ContainerActivity.class);
////            intent.putExtra("isMessage", true);
//            intent.putExtra("isMessageClicked", true);
//
//            intent.putExtra("visitor",userModel.getUserID());
//            Log.i("visitorID", "onCreateView: "+intent);
//
//            context.startActivity(intent);
            appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.containerFrame,chattingFragment).addToBackStack(null).commit();//

        });





        try {
            if (userModel.getStatus().equals("online")){
                holder.sOnline.setVisibility(View.VISIBLE);
                holder.sOffline.setVisibility(View.GONE);
            }else {
                holder.sOnline.setVisibility(View.GONE);
                holder.sOffline.setVisibility(View.VISIBLE);
            }

            Log.i("tag", "onBindViewHolder: ");

        }catch (Exception e){

            holder.sOnline.setVisibility(View.GONE);
            holder.sOffline.setVisibility(View.VISIBLE);

            Log.i("tag", "onBindViewHolder: ");


        }




lastmessage(userModel.getUserID(),holder.lastmessage);












    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }


    public void lastmessage(String othersUserId, TextView last_mess){
        lastMess="default";


        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("chat");
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId=firebaseUser.getUid();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatListModelList.clear();
                List<ChatListModel> tempoChatList = new ArrayList<>();

                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {

                    ChatListModel chatModel = dataSnapshot.getValue(ChatListModel.class);
                    tempoChatList.add(chatModel);

                    assert chatModel != null;
//                    Log.i("TAG", "Chatting Fragment others ID: "+ othersUserId);
                    try {
                        if (
                                chatModel.getReceiverId().equals(currentUserId) && chatModel.getSenderId().equals(othersUserId)
                                        ||
                                        chatModel.getReceiverId().equals(othersUserId) && chatModel.getSenderId().equals(currentUserId)
                        ) {
                            chatListModelList.add(chatModel);
                            lastMess=chatModel.getMessage();

                        }
                    }
                    catch (Exception e){
                        if (
                                currentUserId.equals(tempoChatList.get(0).getReceiverId())
                                        ||
                                        currentUserId.equals( tempoChatList.get(0).getSenderId())
                        ) {
                            chatListModelList.add(chatModel);
                            lastMess=chatModel.getMessage();

                        }
                    }

                    switch (lastMess){
                        case "default":
                            last_mess.setText("No Message");
                            break;
                        default:
                            last_mess.setText(lastMess);
                            break;

                    }

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }





}
