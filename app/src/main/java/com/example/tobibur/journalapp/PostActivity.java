package com.example.tobibur.journalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.example.tobibur.journalapp.helper.DatabaseHandler;
import com.example.tobibur.journalapp.model.Journal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PostActivity extends AppCompatActivity {

    AppCompatEditText postEditText;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        db = new DatabaseHandler(this);
        postEditText = findViewById(R.id.edit_post);

    }

    private void makepost() {
        if(postEditText.getText().toString().isEmpty()){
            postEditText.setError("Your post is empty");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);
        String currDatTime = sdf.format(new Date());
        String post = postEditText.getText().toString().trim();
        Log.d("Insert: ", "Inserting ..");
        db.addPOST(new Journal(post,currDatTime));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.save_btn) {
            makepost();
            Intent intent = new Intent(PostActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
