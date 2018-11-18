package com.example.tobibur.journalapp.database;

import android.content.Context;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class JournalDaoTest {

    @Rule
    public ActivityTestRule<MainActivity> menuActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    private JournalDatabase journalDb;
    private PostViewModel mPostViewModel;
    private MainViewModel mViewModel;
    private JournalModel samePost;
    final JournalModel post = new JournalModel("Kubjek","/storage/emulated/0/Android/data/com.example.tobibur.journalapp/files/Pictures/JPEG_20181107_161358_2103868566.jpg","25/08/2018 12:16:33");

    @Before
    public void setUp() throws Exception {
        // Context context = ApplicationProvider.getApplicationContext();
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
    public void tearDown() throws Exception {
        journalDb.close();
    }

    @Test
    public void insertPost() throws Exception {
        // final JournalModel post = TestHelper.getMockData().get(2);

        mPostViewModel.addPost(post);
        Thread.sleep(1000);

        assertEquals(inDatabase(post), true);
    }

    @Test
    public void updatePost() throws Exception {
        String postText = "Update post";
        // final JournalModel post = TestHelper.getMockData().get(1);

        mPostViewModel.addPost(post);
        Thread.sleep(1000);
        assertEquals(inDatabase(post), true);

        samePost.setPost(postText);
        mViewModel.updatePost(samePost);
        Thread.sleep(1000);

        assertEquals(inDatabase(samePost), true);
    }

    @Test
    public void deleteItem() throws Exception {
        // final JournalModel post = TestHelper.getMockData().get(3);

        mPostViewModel.addPost(post);
        Thread.sleep(1000);
        assertEquals(inDatabase(post), true);

        mViewModel.deletePost(samePost);
        Thread.sleep(1000);

        assertEquals(inDatabase(samePost), false);
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