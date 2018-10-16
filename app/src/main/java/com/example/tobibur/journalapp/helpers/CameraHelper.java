package com.example.tobibur.journalapp.helpers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ImageView;

import com.example.tobibur.journalapp.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraHelper {

    private Context context;
    private String currentPhotoPath;
    private static final double DEFAULT_MAX_BITMAP_DIMENSION = 512.0;

    public CameraHelper(Context context) {
        this.context = context;
    }

    public String getCurrentPhotoPath() {
        return currentPhotoPath;
    }
    public void setCurrentPhotoPath(String currentPhotoPath) {
        this.currentPhotoPath = currentPhotoPath;
    }

    public Intent dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context,
                        "com.example.tobibur.journalapp",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                return takePictureIntent;
            }
        }
        return null;
    }

    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void setPhoto(ImageView photoView, String photoPath) {
        if (photoView != null && photoPath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
            if (bitmap != null) {
                int nh = (int) (bitmap.getHeight() * (DEFAULT_MAX_BITMAP_DIMENSION / bitmap.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, (int) DEFAULT_MAX_BITMAP_DIMENSION, nh, true);
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                Bitmap rotatedBitmap = Bitmap.createBitmap(scaled, 0, 0, scaled.getWidth(), scaled.getHeight(), matrix, true);
                photoView.setImageBitmap(rotatedBitmap);
            }
        }
    }

    public boolean deletePhoto(String photoPath) {
        if (photoPath != null) {
            File oldPhoto = new File(photoPath);
            if (oldPhoto.exists() && oldPhoto.delete()) {
                return true;
            }
        }
        return false;
    }

    public Intent takeDisplayPhotoIntent(final String imagePath) {
        if(imagePath == null) {
            return null;
        }
        try {
            Intent myIntent = new Intent(android.content.Intent.ACTION_VIEW);
            File file = new File(imagePath);
            String extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());
            String mimetype = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
            myIntent.setDataAndType(Uri.fromFile(file), mimetype);
            return myIntent;
        } catch (Exception e) {
            return null;
        }
    }
}
