package com.openclassrooms.realestatemanager.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.openclassrooms.realestatemanager.models.local.immovables.Picture;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class LocalStorageHelper {

    private static final String FOLDER_NAME = "ImmovablePictures";

    public static File createOrGetFile(File destination, String fileName){
        File folder = new File(destination, FOLDER_NAME);
        return new File(folder, fileName);
    }

    public static void saveToInternalStorage(Bitmap bitmap, File file, int source){
        FileOutputStream fos = null;
        try {
            file.getParentFile().mkdirs();
            fos = new FileOutputStream(file);
            if(source == 0){
                // on writing
                Bitmap resized = resizeBitmap(bitmap);
                // Use the compress method on the BitMap object to write image to the OutputStream
                resized.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            } else if (source == 1) {
                // on downloading
                // Use the compress method on the BitMap object to write image to the OutputStream
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
                // throw something to tell it's finished !
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap resizeBitmap(Bitmap bitmap){
        Bitmap resized = Bitmap.createScaledBitmap(bitmap,(int)(bitmap.getWidth()*0.4), (int)(bitmap.getHeight()*0.4), true);
        return resized;
    }

}

