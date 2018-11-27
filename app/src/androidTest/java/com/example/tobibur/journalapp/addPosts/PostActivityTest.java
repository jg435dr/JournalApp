package com.example.tobibur.journalapp.addPosts;

import android.content.Context;

import com.example.tobibur.journalapp.R;
import com.example.tobibur.journalapp.database.JournalDao;
import com.example.tobibur.journalapp.database.JournalDatabase;
import com.example.tobibur.journalapp.database.JournalModel;
import com.example.tobibur.journalapp.viewPosts.MainActivity;
import com.example.tobibur.journalapp.viewPosts.MainViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class PostActivityTest {

    @Rule
    public ActivityTestRule<PostActivity> postActivityActivityTestRule =
            new ActivityTestRule<>(PostActivity.class);


    @Test
    public void clickOnAddPostButton() {
        String message = "Click on Add Post Button";
        onView(withId(R.id.edit_post))
                .check(matches(isDisplayed()))
                .perform(typeText(message));


        onView(withId(R.id.fabPost))
                .check(matches(isDisplayed()))
                .perform(click());

        // mozes este vyskusat result
    }
}