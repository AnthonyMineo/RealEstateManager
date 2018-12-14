package com.openclassrooms.realestatemanager.utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import android.util.Log;


import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class FirebaseStorageHelper {

    private static final String FOLDER_NAME = "ImmovablePictures/";
    private static volatile FirebaseStorage storage = FirebaseStorage.getInstance();
    private static final String TAG = "FirebaseStorageHelper";

    // --- FIREBASE IMAGE REFERENCE ---
    private static StorageReference getFirebaseImageRef(String fileName){
        return storage.getReference().child(FOLDER_NAME + fileName);
    }

    // --- UPLOAD FROM FILE ---
    public static void UploadImageFromAFile(String path, String fileName){
        Uri file = Uri.fromFile(new File(path + fileName));
        UploadTask uploadTask = getFirebaseImageRef(file.getLastPathSegment()).putFile(file);

        // Listen for state changes, errors, and completion of the upload.
        uploadTask.addOnProgressListener(taskSnapshot -> {
            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
            Log.i(TAG, "Upload is " + progress + "% done");
        }).addOnPausedListener(taskSnapshot -> {
            // Handle paused uploads
            Log.i(TAG, "Upload is paused");
        }).addOnFailureListener(exception -> {
            // Handle unsuccessful uploads
            Log.i(TAG, "Upload failed");
        }).addOnSuccessListener(taskSnapshot -> {
            // Handle successful uploads on complete
            Log.i(TAG, "Upload complete");
        });
    }

    // --- UPLOAD FROM URI ---
    public static void UploadImageFromAURI(String uri, String fileName){
        Uri file = Uri.parse(uri);
        UploadTask uploadTask = getFirebaseImageRef(fileName).putFile(file);

        // Listen for state changes, errors, and completion of the upload.
        uploadTask.addOnProgressListener(taskSnapshot -> {
            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
            Log.i(TAG, "Upload is " + progress + "% done");
        }).addOnPausedListener(taskSnapshot -> {
            // Handle paused uploads
            Log.i(TAG, "Upload is paused");
        }).addOnFailureListener(exception -> {
            // Handle unsuccessful uploads
            Log.i(TAG, "Upload failed");
        }).addOnSuccessListener(taskSnapshot -> {
            // Handle successful uploads on complete
            Log.i(TAG, "Upload complete");
        });
    }


    // --- UPLOAD FROM BITMAP ---
    public static void UploadImageFromBitmap(Bitmap bitmap, String fileName) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap resized = LocalStorageHelper.resizeBitmap(bitmap);
        resized.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] data = baos.toByteArray();
        UploadTask uploadTask = getFirebaseImageRef(fileName).putBytes(data);

        // Listen for state changes, errors, and completion of the upload.
        uploadTask.addOnProgressListener(taskSnapshot -> {
            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
            Log.i(TAG, "Upload is " + progress + "% done");
        }).addOnPausedListener(taskSnapshot -> {
            // Handle paused uploads
            Log.i(TAG, "Upload is paused");
        }).addOnFailureListener(exception -> {
            // Handle unsuccessful uploads
            Log.i(TAG, "Upload failed");
        }).addOnSuccessListener(taskSnapshot -> {
            // Handle successful uploads on complete
            Log.i(TAG, "Upload complete");
        });
    }

    // --- DOWNLOAD ---
    public static void DownloadImage(File destination, String fileName){
       File localFile = null;
        try {
            localFile = File.createTempFile("temp", "png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        File finalLocalFile = localFile;
        getFirebaseImageRef(fileName).getFile(localFile).addOnSuccessListener(taskSnapshot -> {
            // Local temp file has been created
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(finalLocalFile.getAbsolutePath(), bmOptions);
            LocalStorageHelper.saveToInternalStorage(bitmap, LocalStorageHelper.createOrGetFile(destination, fileName), 1);
            // need to tell adapter that upload is complete
            Log.i(TAG, "Download complete");
        }).addOnFailureListener(exception -> {
            // Handle any errors
            int errorCode = ((StorageException) exception).getErrorCode();
            String errorMessage = exception.getMessage();
            Log.i(TAG, "Download failed : " + errorCode + " = " + errorMessage + "destination = " + destination + " fileName = " + fileName);
        });
    }
}
