package com.example.tobibur.journalapp.addPosts;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tobibur.journalapp.R;
import com.example.tobibur.journalapp.helpers.CameraHelper;
import com.example.tobibur.journalapp.database.JournalModel;
import com.example.tobibur.journalapp.viewPosts.MainViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PostActivity extends AppCompatActivity {

    private PostViewModel mPostViewModel;
    private MainViewModel mMainViewModel;
    @BindView(R.id.edit_post) AppCompatEditText postEditText;
    @BindView(R.id.imageView_post_photo) ImageView photoImageView;

    private static final String TAG = "PostActivity";
    private CameraHelper cameraHelper;
    private String photoPath;
    private boolean editPost = false;
    private int ID;
    private JournalModel jModel;

    private static final int CAMERA_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);

        mPostViewModel = ViewModelProviders.of(this).get(PostViewModel.class);
        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        cameraHelper = new CameraHelper(this);
        if ((ID = getIntent().getIntExtra("id", -1)) != -1) {
            mMainViewModel.getPostList().observe(this, new Observer<List<JournalModel>>() {
                @Override
                public void onChanged(@Nullable List<JournalModel> journalModels) {
                    if (journalModels == null || journalModels.isEmpty()) {
                        return;
                    }
                    for(JournalModel journalModel : journalModels) {
                        if(journalModel.getId() == ID) {
                            jModel = journalModel;
                        } else {
                            continue;
                        }
                        if(jModel.getPost() != null) {
                            postEditText.setText(jModel.getPost());
                        }
                        photoPath = jModel.getPhotoPath();
                        cameraHelper.setCurrentPhotoPath(photoPath);
                        if(photoPath != null) {
                            cameraHelper.setPhoto(photoImageView, jModel.getPhotoPath());
                            setOnClickPhoto();
                        }
                        editPost = true;
                    }
                }
            });
        }
    }

    @OnClick(R.id.fabPost)
    public void makePost() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);
        String currDatTime = sdf.format(new Date());
        if(postEditText.getText() == null){
            Toast.makeText(PostActivity.this, "Hi there you missed something", Toast.LENGTH_SHORT).show();
        } else if(editPost) {
            jModel.setPhotoPath(photoPath);
            jModel.setDate_time(currDatTime);
            jModel.setPost(postEditText.getText().toString());
            mMainViewModel.updatePost(jModel);
        } else{
            mPostViewModel.addPost(new JournalModel(
                    postEditText.getText().toString(),
                    photoPath,
                    currDatTime
            ));
        }
        finish();
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
            case R.id.camera_btn:
                startActivityForResult(cameraHelper.dispatchTakePictureIntent(), CAMERA_REQUEST);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setOnClickPhoto() {
        photoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                    if (photoPath != null) {
                        Intent intent = cameraHelper.takeDisplayPhotoIntent(photoPath);
                        if (intent != null) {
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Error -> opening image"
                                    , Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        photoImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(photoPath != null) {
                    new AlertDialog.Builder(PostActivity.this)
                            .setMessage("Are you sure you want to delete this photo?")
                            .setCancelable(true)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    cameraHelper.deletePhoto(photoPath);
                                    photoPath = null;
                                    photoImageView.setImageResource(android.R.color.transparent);
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAMERA_REQUEST) {
            if (resultCode == RESULT_OK) {
                cameraHelper.deletePhoto(photoPath);
                photoPath = cameraHelper.getCurrentPhotoPath();
                cameraHelper.setPhoto(photoImageView, photoPath);
                setOnClickPhoto();
            }
        }
    }
}
