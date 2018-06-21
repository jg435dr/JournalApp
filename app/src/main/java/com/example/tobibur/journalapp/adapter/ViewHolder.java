package com.example.tobibur.journalapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tobibur.journalapp.R;

public class ViewHolder extends RecyclerView.ViewHolder{

    public TextView dateTime, posts;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        dateTime = itemView.findViewById(R.id.date_time_textView);
        posts = itemView.findViewById(R.id.post_textView);
    }
}
