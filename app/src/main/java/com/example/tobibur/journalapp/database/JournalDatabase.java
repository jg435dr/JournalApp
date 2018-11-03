package com.example.tobibur.journalapp.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = JournalModel.class, version = 1)
public abstract class JournalDatabase extends RoomDatabase{

    private static JournalDatabase INSTANCE;

    public static JournalDatabase getDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    JournalDatabase.class,"journal_db")
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }

    public abstract JournalDao getJournalPosts();
}
