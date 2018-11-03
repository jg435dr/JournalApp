package com.example.tobibur.journalapp.addPosts;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import androidx.annotation.NonNull;

import com.example.tobibur.journalapp.database.JournalDatabase;
import com.example.tobibur.journalapp.database.JournalModel;

public class PostViewModel extends AndroidViewModel {

    private JournalDatabase mJournalDatabase;

    public PostViewModel(@NonNull Application application) {
        super(application);
        mJournalDatabase = JournalDatabase.getDatabase(this.getApplication());
    }

    public void addPost(final JournalModel model){
        new addAsyncTask(mJournalDatabase).execute(model);
    }

    private static class addAsyncTask extends AsyncTask<JournalModel, Void, Void>{
        private JournalDatabase db;

        addAsyncTask(JournalDatabase journalDatabase){
            db = journalDatabase;
        }

        @Override
        protected Void doInBackground(final JournalModel... journalModels) {
            db.getJournalPosts().insertPost(journalModels[0]);
            return null;
        }
    }
}
