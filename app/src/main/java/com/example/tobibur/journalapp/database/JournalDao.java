package com.example.tobibur.journalapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface JournalDao {

    @Query("SELECT * FROM JournalModel")
    LiveData<List<JournalModel>> getAllPosts();

    @Query("SELECT * from JournalModel where id = :id")
    JournalModel getPostbyId(String id);

    @Insert(onConflict = REPLACE)
    void insertPost(JournalModel journalModel);

    @Delete
    void deletePost(JournalModel journalModel);
}
