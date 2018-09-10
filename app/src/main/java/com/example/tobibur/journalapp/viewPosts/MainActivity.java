package com.example.tobibur.journalapp.viewPosts;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tobibur.journalapp.R;
import com.example.tobibur.journalapp.adapter.RecyclerAdapter;
import com.example.tobibur.journalapp.addPosts.PostActivity;
import com.example.tobibur.journalapp.database.JournalModel;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.jetradar.desertplaceholder.DesertPlaceholder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnLongClickListener, View.OnClickListener{

    @BindView(R.id.recyclerView_id) RecyclerView recyclerView;
    @BindView(R.id.nothing_text) DesertPlaceholder textView;
    @BindView(R.id.toolbar) Toolbar toolbar;

    private MainViewModel mViewModel;
    private RecyclerAdapter adapter;
    LayoutAnimationController mController=null;

    private static final String PREFS_NAME = "prefs";
    private static final String IS_FIRST_LAUNCH = "is_first";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        textView.setOnButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        retrieveData();

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        boolean isFirst = settings.getBoolean(IS_FIRST_LAUNCH, true);
        if (isFirst){
            settings.edit().putBoolean(IS_FIRST_LAUNCH, false).apply();
            showTips();
        }
    }

    @OnClick(R.id.fab)
    public void onFabButtonClicked(){
        addNewPost();
    }

    private void showTips() {
        TapTargetView.showFor(this,                 // `this` is an Activity
                TapTarget.forView(findViewById(R.id.fab), "Add a new post!", "Tap this button for new post")
                        // All options below are optional
                        .outerCircleColor(R.color.colorAccent)      // Specify a color for the outer circle
                        .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                        .targetCircleColor(R.color.colorWhite)   // Specify a color for the target circle
                        .titleTextSize(20)                  // Specify the size (in sp) of the title text
                        .titleTextColor(R.color.colorWhite)      // Specify the color of the title text
                        .descriptionTextSize(10)            // Specify the size (in sp) of the description text
                        .descriptionTextColor(R.color.colorAccent)  // Specify the color of the description text
                        .textColor(R.color.colorPrimary)            // Specify a color for both the title and description text
                        .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                        .dimColor(R.color.colorBlack)            // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                        .cancelable(true)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(true)                   // Whether to tint the target view's color
                        .transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
                        .icon(getResources().getDrawable(R.drawable.ic_add_black_24dp))                     // Specify a custom drawable to draw as the target
                        .targetRadius(60),                  // Specify the target radius (in dp)
                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);      // This call is optional
                        addNewPost();
                    }
                });
    }

    private void addNewPost() {
        startActivity(new Intent(getApplicationContext(),PostActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void retrieveData() {

        mController = AnimationUtils.loadLayoutAnimation(this,R.anim.layout_fall_down);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerAdapter(new ArrayList<JournalModel>(), this,this);
        recyclerView.setLayoutAnimation(mController);
        recyclerView.setAdapter(adapter);
        mViewModel.getPostList().observe(this, new Observer<List<JournalModel>>() {
            @Override
            public void onChanged(@Nullable List<JournalModel> journalModels) {
                adapter.addPost(journalModels);
                if (journalModels != null && journalModels.size() < 1) {
                    recyclerView.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);
                }else {
                    recyclerView.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public boolean onLongClick(View view) {
        deleteDialog(view);
        return true;
    }

    private void deleteDialog(final View view) {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to delete this post?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        JournalModel journalModel = (JournalModel) view.getTag();
                        mViewModel.deletePost(journalModel);
                        Toast.makeText(getApplicationContext()
                                , journalModel.getPost()+"->Just deleted"
                                , Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }

    @Override
    public void onClick(View view) {
        final JournalModel journalModel = (JournalModel) view.getTag();
        new AlertDialog.Builder(this)
                .setTitle(journalModel.getDate_time())
                .setMessage(journalModel.getPost())
                .setCancelable(true)
                .setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        updateDialog(journalModel);
                    }
                })
                .setPositiveButton("Close", null)
                .show();
    }

    private void updateDialog(final JournalModel journalModel) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.custom_dialog, null);
        final EditText mEditText = alertLayout.findViewById(R.id.dMessage);
        mEditText.setText(journalModel.getPost());
        new AlertDialog.Builder(this)
                .setTitle(journalModel.getDate_time())
                .setView(alertLayout)
                .setCancelable(true)
                .setNegativeButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);
                        String currDatTime = sdf.format(new Date());
                        journalModel.setDate_time(currDatTime);
                        journalModel.setPost(mEditText.getText().toString());
                        mViewModel.updatePost(journalModel);
                    }
                })
                .setPositiveButton("Close", null)
                .show();
    }
}
