package com.example.tobibur.journalapp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by admin on 2017-10-11.
 */
@Entity
public class JournalModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String post;
    private String date_time;
    private String photoPath;

    public JournalModel(String post, String photoPath, String date_time) {
        this.post = post;
        this.date_time = date_time;
        this.photoPath = photoPath;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }
}
