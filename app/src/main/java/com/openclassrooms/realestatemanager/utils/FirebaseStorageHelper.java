package com.openclassrooms.realestatemanager.utils;

import android.graphics.Bitmap;
import android.net.Uri;

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
            System.out.println("Upload is " + progress + "% done");
        }).addOnPausedListener(taskSnapshot -> {
            // Handle paused uploads
            System.out.println("Upload is paused");
        }).addOnFailureListener(exception -> {
            // Handle unsuccessful uploads
            System.out.println("Upload failed");
        }).addOnSuccessListener(taskSnapshot -> {
            // Handle successful uploads on complete
            System.out.println("Upload complete");
        });
    }

    // --- UPLOAD FROM BITMAP ---
    public static void UploadImageFromBitmap(Bitmap bitmap, String fileName) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = getFirebaseImageRef(fileName).putBytes(data);

        // Listen for state changes, errors, and completion of the upload.
        uploadTask.addOnProgressListener(taskSnapshot -> {
            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
            System.out.println("Upload is " + progress + "% done");
        }).addOnPausedListener(taskSnapshot -> {
            // Handle paused uploads
            System.out.println("Upload is paused");
        }).addOnFailureListener(exception -> {
            // Handle unsuccessful uploads
            System.out.println("Upload failed");
        }).addOnSuccessListener(taskSnapshot -> {
            // Handle successful uploads on complete
            System.out.println("Upload complete");
        });
    }

    // --- DOWNLOAD ---
    public static void DownloadImage(String fileName){
        File localFile = null;
        try {
            localFile = File.createTempFile(fileName, "png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        getFirebaseImageRef(fileName).getFile(localFile).addOnSuccessListener(taskSnapshot -> {
            // Local temp file has been created
            System.out.println("Download complete");
        }).addOnFailureListener(exception -> {
            // Handle any errors
            int errorCode = ((StorageException) exception).getErrorCode();
            String errorMessage = exception.getMessage();
            System.out.println("Download failed : " + errorCode + " = " + errorMessage);
        });
    }
}
