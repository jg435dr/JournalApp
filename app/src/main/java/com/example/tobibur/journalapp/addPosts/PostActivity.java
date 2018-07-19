package com.example.tobibur.journalapp.addPosts;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tobibur.journalapp.R;
import com.example.tobibur.journalapp.database.JournalModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostActivity extends AppCompatActivity {

    private PostViewModel mPostViewModel;
    @BindView(R.id.edit_post) AppCompatEditText postEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);

        mPostViewModel = ViewModelProviders.of(this).get(PostViewModel.class);
    }

    private void makePost() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);
        String currDatTime = sdf.format(new Date());
        if(postEditText.getText() == null){
            Toast.makeText(PostActivity.this, "Hi there you missed something", Toast.LENGTH_SHORT).show();
        }else{
            mPostViewModel.addPost(new JournalModel(
                    postEditText.getText().toString(),
                    currDatTime
            ));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.save_btn) {
            makePost();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
