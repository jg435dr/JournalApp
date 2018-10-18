package com.example.tobibur.journalapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface JournalDao {

    @Query("SELECT * FROM JournalModel ORDER BY date_time DESC")
    LiveData<List<JournalModel>> getAllPosts();

    @Query("SELECT * from JournalModel where id = :id LIMIT 1")
    JournalModel getPostbyId(int id);

    @Insert(onConflict = REPLACE)
    void insertPost(JournalModel journalModel);

    @Delete
    void deletePost(JournalModel journalModel);

    @Update
    void updatePost(JournalModel journalModel);

//    @Query("SELECT * from JournalModel where id = :id")
//    public abstract List<JournalModel> getPostbyId(String id);
}
