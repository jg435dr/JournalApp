package com.example.tobibur.journalapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tobibur.journalapp.R;
import com.example.tobibur.journalapp.model.Journal;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder>{

    //private List<String> dateTime;
    private List<Journal> posts;
    Context context;

    public RecyclerAdapter(List<Journal> posts, Context context) {
        //this.dateTime = dateTime;
        this.posts = posts;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Journal post = posts.get(i);
        viewHolder.dateTime.setText(post.get_date_time());
        viewHolder.posts.setText(post.get_Journal_data());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
