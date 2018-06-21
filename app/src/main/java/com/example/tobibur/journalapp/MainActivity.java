package com.example.tobibur.journalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tobibur.journalapp.adapter.RecyclerAdapter;
import com.example.tobibur.journalapp.helper.DatabaseHandler;
import com.example.tobibur.journalapp.model.Journal;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    DatabaseHandler db;
    String log1="Nothing here";
    RecyclerView recyclerView;
    private List<Journal> posts;
    TextView textView;

    Integer dbPostCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DatabaseHandler(this);
        recyclerView = findViewById(R.id.recyclerView_id);
        textView = findViewById(R.id.nothing_text);

        retriveData();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                startActivity(new Intent(getApplicationContext(),PostActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        retriveData();
    }

    private void retriveData() {
        dbPostCount = db.getPostsCount();

        if(dbPostCount>=1){
            posts = db.getAllPosts();

        }else {
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setHasFixedSize(true);
        RecyclerAdapter adapter = new RecyclerAdapter(posts, this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
}
