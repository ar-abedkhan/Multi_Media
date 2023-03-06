package com.abedkhan.multimedia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abedkhan.multimedia.AllViewHolder.ChatViewholder;
import com.abedkhan.multimedia.Model.ChatListModel;
import com.abedkhan.multimedia.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatViewholder> {
    List<ChatListModel>chatModelList;
    Context context;
    String myUserId;
    final int SENDER_UI =1,RECEIVER_UI =2;


    public ChatAdapter(List<ChatListModel> chatModelList, Context context, String myUserId) {
        this.chatModelList = chatModelList;
        this.context = context;
        this.myUserId = myUserId;
    }

    @NonNull
    @Override
    public ChatViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        if (viewType == SENDER_UI){
            view= LayoutInflater.from(context).inflate(R.layout.message_sender_design,parent,false);
        }
        else {
            view=LayoutInflater.from(context).inflate(R.layout.message_reciver_design,parent,false);
        }

        return new ChatViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewholder holder, int position) {
       ChatListModel chatModel=chatModelList.get(position);

       long time= Long.parseLong(String.valueOf(chatModel.getTimeMillis()));
       holder.message.setText(chatModel.getMessage());

        Date date =new Date(time);
        DateFormat dateFormat=new SimpleDateFormat("hh:mm a");


//        Glide.with(context).load(chatModel.getSend_img()).into(holder.image);
//        if (! holder.message.equals("")){

            holder.messtime.setText(dateFormat.format(date));

//        }else {
//            holder.message.setError("Write a message");
//
//        }
    }

    @Override
    public int getItemCount() {
        return chatModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (chatModelList.get(position).getSenderId().equals(myUserId)){
            return SENDER_UI;
        }else {
            return RECEIVER_UI;
        }
    }
}
