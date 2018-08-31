package com.example.tobibur.journalapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tobibur.journalapp.R;
import com.example.tobibur.journalapp.database.JournalModel;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder>{

    private List<JournalModel> posts;
    private View.OnLongClickListener longClickListener;
    private View.OnClickListener mOnClickListener;

    public RecyclerAdapter(List<JournalModel> posts, View.OnLongClickListener longClickListener, View.OnClickListener onClickListener) {
        this.posts = posts;
        this.longClickListener = longClickListener;
        this.mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        JournalModel post = posts.get(i);
        viewHolder.dateTime.setText(post.getDate_time());
        viewHolder.posts.setText(post.getPost());
        viewHolder.itemView.setTag(post);
        viewHolder.itemView.setOnLongClickListener(longClickListener);
        viewHolder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void addPost(List<JournalModel> journalModels){
        this.posts = journalModels;
        notifyDataSetChanged();
    }
}
