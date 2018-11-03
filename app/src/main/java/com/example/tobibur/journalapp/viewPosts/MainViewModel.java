package com.example.tobibur.journalapp.viewPosts;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;
import androidx.annotation.NonNull;

import com.example.tobibur.journalapp.database.JournalDatabase;
import com.example.tobibur.journalapp.database.JournalModel;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private LiveData<List<JournalModel>> PostList;

    private JournalDatabase mJournalDatabase;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mJournalDatabase = JournalDatabase.getDatabase(this.getApplication());
        PostList = mJournalDatabase.getJournalPosts().getAllPosts();
    }

    public LiveData<List<JournalModel>> getPostList() {
        return PostList;
    }

    public void deletePost(JournalModel model){
        new deleteAsyncTask(mJournalDatabase).execute(model);
    }

    private static class deleteAsyncTask extends AsyncTask<JournalModel, Void, Void>{

        private JournalDatabase mJournalDatabase;

        deleteAsyncTask(JournalDatabase database){
            mJournalDatabase = database;
        }

        @Override
        protected Void doInBackground(JournalModel... journalModels) {
            mJournalDatabase.getJournalPosts().deletePost(journalModels[0]);
            return null;
        }
    }

    public void updatePost(JournalModel model){
        new updateAsyncTask(mJournalDatabase).execute(model);
    }

    private static class updateAsyncTask extends AsyncTask<JournalModel, Void, Void>{

        private JournalDatabase mJournalDatabase;

        updateAsyncTask(JournalDatabase database){
            mJournalDatabase = database;
        }

        @Override
        protected Void doInBackground(JournalModel... journalModels) {
            mJournalDatabase.getJournalPosts().updatePost(journalModels[0]);
            return null;
        }
    }
}
