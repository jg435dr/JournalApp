package com.example.tobibur.journalapp.viewPosts;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.example.tobibur.journalapp.R;
import com.example.tobibur.journalapp.TestHelper;
import com.example.tobibur.journalapp.addPosts.PostActivity;
import com.example.tobibur.journalapp.addPosts.PostViewModel;
import com.example.tobibur.journalapp.database.JournalDao;
import com.example.tobibur.journalapp.database.JournalDatabase;
import com.example.tobibur.journalapp.database.JournalModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.espresso.contrib.RecyclerViewActions;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest{

    @Rule
    public ActivityTestRule<MainActivity> menuActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    private JournalDatabase journalDb;
    private PostViewModel mPostViewModel;
    private MainViewModel mViewModel;
    private List<JournalModel> mockData;

    @Before
    public void setUp() throws Exception {

        Context appContext = InstrumentationRegistry.getTargetContext();
        mPostViewModel = ViewModelProviders.of(menuActivityTestRule.getActivity()).get(PostViewModel.class);
        mViewModel = ViewModelProviders.of(menuActivityTestRule.getActivity()).get(MainViewModel.class);
        journalDb = Room.inMemoryDatabaseBuilder(appContext, JournalDatabase.class).build();

        mockData = TestHelper.getMockData();
        MutableLiveData<List<JournalModel>> liveData = new MutableLiveData<>();
        liveData.postValue(mockData);

        mViewModel = Mockito.mock(MainViewModel.class);
        Mockito.when(mViewModel.getPostList()).thenReturn(liveData);
        menuActivityTestRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                menuActivityTestRule.getActivity().recreate();
            }
        });
    }

    @After
    public void tearDown() throws Exception {
        journalDb.close();
    }

    @Test
    public void checkMockData() {
        List<JournalModel> dbJournalModels = mViewModel.getPostList().getValue();
        assertNotNull(dbJournalModels);
        int counter = 0;
        for (int i = 0; i < dbJournalModels.size(); i++) {
            JournalModel journalModel = mockData.get(i);
            JournalModel dbJournalModel = dbJournalModels.get(i);
            if (journalModel.getPost().equals(dbJournalModel.getPost()) &&
                    journalModel.getPhotoPath().equals(dbJournalModel.getPhotoPath()) &&
                    journalModel.getDate_time().equals(dbJournalModel.getDate_time())) {
                counter++;
            }
        }
        assertEquals(counter, dbJournalModels.size());
    }

    @Test
    public void createPost() throws Exception{
        onView(withId(R.id.fab))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    @Test
    public void onEditPost() throws Exception {
        onView(ViewMatchers.withId(R.id.recyclerView_id))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withText("Close")).perform(click());

        onView(ViewMatchers.withId(R.id.recyclerView_id))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withText("Edit")).perform(click());

        // mozes este skusit intentresult
    }

    @Test
    public void deletePost() {
        final JournalModel post = mockData.get(3);
        mPostViewModel.addPost(post);

        onView(ViewMatchers.withId(R.id.recyclerView_id))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, longClick()));

        onView(withText("Are you sure you want to delete this post?"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()));

        onView(withText("Yes")).perform(click());
    }
}