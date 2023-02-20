package com.abedkhan.multimedia.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abedkhan.multimedia.AllViewHolder.NotificationViewHolder;
import com.abedkhan.multimedia.Model.NotificationModel;
import com.abedkhan.multimedia.Model.UserModel;
import com.abedkhan.multimedia.R;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {
    Context context;
    List<NotificationModel> notiList;

    DatabaseReference databaseReference;

    public NotificationAdapter(Context context, List<NotificationModel> notiList) {
        this.context = context;
        this.notiList = notiList;
        Log.i("TAG", "NotificationAdapter: "+ notiList.size());
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationViewHolder(LayoutInflater.from(context).inflate(R.layout.notification_recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        NotificationModel model = notiList.get(position);

//        holder.notiMainTxt.setText(model.getNotificationTxt());

        //        --Setting react or comment time
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date date = new Date(model.getNotiTimeMillis());
        holder.notiTimeMillis.setText(simpleDateFormat.format(date));

//        #Getting Notification Owner name and owner Profile Image
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("User").child(model.getPerformerID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userModel = snapshot.getValue(UserModel.class);

                if (model.getNotificationTxt().equals("comment")){
                    holder.notiMainTxt.setText(userModel.getFullName()+" has commented on your post.");
                }else {
                    holder.notiMainTxt.setText(userModel.getFullName()+" has reacted on your post.");
                }
                Glide.with(context).load(userModel.getProfileImgUrl()).placeholder(R.drawable.lightning_tree).into(holder.profileImg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return notiList.size();
    }
}
