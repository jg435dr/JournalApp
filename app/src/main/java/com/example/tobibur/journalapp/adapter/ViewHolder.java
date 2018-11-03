package com.example.tobibur.journalapp.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tobibur.journalapp.R;

public class ViewHolder extends RecyclerView.ViewHolder{

    public TextView dateTime, posts;
    ImageView postImage;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        dateTime = itemView.findViewById(R.id.date_time_textView);
        posts = itemView.findViewById(R.id.post_textView);
        postImage = itemView.findViewById(R.id.post_image);
    }
}
