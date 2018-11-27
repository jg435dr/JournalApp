package com.example.tobibur.journalapp.database;

import android.content.Context;

import com.example.tobibur.journalapp.TestHelper;
import com.example.tobibur.journalapp.addPosts.PostViewModel;
import com.example.tobibur.journalapp.viewPosts.MainActivity;
import com.example.tobibur.journalapp.viewPosts.MainViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class JournalDaoTest {

    @Rule
    public ActivityTestRule<MainActivity> menuActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    private JournalDatabase journalDb;
    private PostViewModel mPostViewModel;
    private MainViewModel mViewModel;
    private JournalModel samePost;

    @Before
    public void setUp() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        journalDb = Room.inMemoryDatabaseBuilder(appContext, JournalDatabase.class).build();
        mPostViewModel = ViewModelProviders.of(menuActivityTestRule.getActivity()).get(PostViewModel.class);
        mViewModel = ViewModelProviders.of(menuActivityTestRule.getActivity()).get(MainViewModel.class);


        List<JournalModel> journalModels = mViewModel.getPostList().getValue();
        if (journalModels != null && !journalModels.isEmpty()) {
            for(JournalModel journalModel : journalModels) {
                mViewModel.deletePost(journalModel);
            }
        }
    }

    @After
    public void tearDown(){
        journalDb.close();
    }

    @Test
    public void insertPost() throws Exception {
        final JournalModel post = TestHelper.getMockData().get(2);

        mPostViewModel.addPost(post);
        Thread.sleep(1000);

        assertTrue(inDatabase(post));
    }

    @Test
    public void updatePost() throws Exception {
        String postText = "Update post";
        final JournalModel post = TestHelper.getMockData().get(1);

        mPostViewModel.addPost(post);
        Thread.sleep(1000);
        assertTrue(inDatabase(post));

        samePost.setPost(postText);
        mViewModel.updatePost(samePost);
        Thread.sleep(1000);

        assertTrue(inDatabase(samePost));
    }

    @Test
    public void deleteItem() throws Exception {
        final JournalModel post = TestHelper.getMockData().get(3);

        mPostViewModel.addPost(post);
        Thread.sleep(1000);
        assertTrue(inDatabase(post));

        mViewModel.deletePost(samePost);
        Thread.sleep(1000);

        assertFalse(inDatabase(samePost));
    }

    private boolean inDatabase(JournalModel post) {
        List<JournalModel> journalModels = mViewModel.getPostList().getValue();
        assertNotNull(journalModels);
        for (JournalModel journalModel : journalModels) {
            if (journalModel.getPost().equals(post.getPost()) &&
                    journalModel.getDate_time().equals(post.getDate_time()) &&
                    journalModel.getPhotoPath().equals(post.getPhotoPath())) {
                samePost = journalModel;
                return true;
            }
        }
        return false;
    }
}