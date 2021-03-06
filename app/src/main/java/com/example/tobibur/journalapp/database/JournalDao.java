package com.example.tobibur.journalapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

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
}
