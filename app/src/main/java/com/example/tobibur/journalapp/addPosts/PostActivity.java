package com.example.tobibur.journalapp.addPosts;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tobibur.journalapp.R;
import com.example.tobibur.journalapp.adapter.CameraAdapter;
import com.example.tobibur.journalapp.database.JournalModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostActivity extends AppCompatActivity {

    private PostViewModel mPostViewModel;
    @BindView(R.id.edit_post) AppCompatEditText postEditText;

    private static final String TAG = "PostActivity";
    private CameraAdapter cameraAdapter;

    private static final int CAMERA_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);

        mPostViewModel = ViewModelProviders.of(this).get(PostViewModel.class);
        cameraAdapter = new CameraAdapter(this);
    }

    private void makePost() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);
        String currDatTime = sdf.format(new Date());
        if(postEditText.getText() == null){
            Toast.makeText(PostActivity.this, "Hi there you missed something", Toast.LENGTH_SHORT).show();
        }else{
            mPostViewModel.addPost(new JournalModel(
                    postEditText.getText().toString(),
                    cameraAdapter.getCurrentPhotoPath(),
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
        switch (item.getItemId()) {
            case R.id.save_btn:
                makePost();
                finish();
                return true;
            case R.id.camera_btn:
                startActivityForResult(cameraAdapter.dispatchTakePictureIntent(), CAMERA_REQUEST);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAMERA_REQUEST) {
            if (resultCode == RESULT_OK) {
                cameraAdapter.setPhoto((ImageView) findViewById(R.id.imageView_post_photo), cameraAdapter.getCurrentPhotoPath());
            }
        }
    }
}
