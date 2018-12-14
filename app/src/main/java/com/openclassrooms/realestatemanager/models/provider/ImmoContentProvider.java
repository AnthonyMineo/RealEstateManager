package com.openclassrooms.realestatemanager.models.provider;

import android.arch.persistence.room.Room;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.openclassrooms.realestatemanager.models.database.RealEstateDB;
import com.openclassrooms.realestatemanager.models.local.immovables.Immo;


public class ImmoContentProvider extends ContentProvider {

    // FOR DATA
    public RealEstateDB mRealEstateDB;
    public static final String AUTHORITY = "com.openclassrooms.realestatemanager.models.provider";
    public static final String TABLE_NAME = Immo.class.getSimpleName();
    public static final Uri URI_ITEM = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

    @Override
    public boolean onCreate() {
        mRealEstateDB = Room.databaseBuilder(getContext().getApplicationContext(),
                RealEstateDB.class, "RealEstateDB.db")
                .build();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        if (getContext() != null){

            final Cursor cursor = mRealEstateDB.immoDao().getAllImmoWithCursor();
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;

        }
        throw new IllegalArgumentException("Failed to query row for uri " + uri);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "vnd.android.cursor.item/" + AUTHORITY + "." + TABLE_NAME;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        if (getContext() != null){
            final long id = mRealEstateDB.immoDao().insertImmo(Immo.fromContentValues(contentValues));
            if (id != 0){
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            }
        }
        throw new IllegalArgumentException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
