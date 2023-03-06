package com.abedkhan.multimedia.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListViewHolders> {
//    List<ChatListModel> chatListModelList;
    List<UserModel> userModelList;
    Context context;
    PostListener listener;

    public ChatListAdapter(List<UserModel> userModelList, Context context, PostListener listener) {
        this.userModelList = userModelList;
        this.context = context;
        this.listener = listener;
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

//         listener.gotoFragmentWithValue(new ChattingFragment(), userModel.getUserID());
         
////            Log.i("tag", "listener mess: ");
//
//
//
            AppCompatActivity appCompatActivity= (AppCompatActivity) view.getContext();
            ChattingFragment chattingFragment=new ChattingFragment();
//            passing post data to the fragment
            Bundle bundle = new Bundle();
            bundle.putString("visitor", userModel.getUserID());
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




    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }
}
