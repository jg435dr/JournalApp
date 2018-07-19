package com.example.tobibur.journalapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
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
