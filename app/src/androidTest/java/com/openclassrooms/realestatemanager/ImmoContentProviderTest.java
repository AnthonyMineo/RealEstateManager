package com.openclassrooms.realestatemanager;

import android.arch.persistence.room.Room;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.realestatemanager.models.database.RealEstateDB;
import com.openclassrooms.realestatemanager.models.local.Agent;
import com.openclassrooms.realestatemanager.models.local.immovables.Immo;
import com.openclassrooms.realestatemanager.models.local.immovables.Vicinity;
import com.openclassrooms.realestatemanager.models.provider.ImmoContentProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(AndroidJUnit4.class)
public class ImmoContentProviderTest {

    // FOR DATA
    private RealEstateDB database;
    private ContentResolver mContentResolver;

    // DATA SET FOR TEST
    private static int AGENT_ID = 1;
    private static Agent AGENT_DEMO = new Agent(AGENT_ID, "Anthony", "anthony.mineo.pro@gmail.com", "none");

    private static Vicinity vic1 = new Vicinity("10 rue du test", null, "Android", "32000", "World");
    private static String description = "Hello test !";
    private static Immo IMMO_1 = new Immo("Apartment",  210000, 70, 5, 1, 1, description, vic1, 20181114, AGENT_ID);


    @Before
    public void initDb() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                RealEstateDB.class)
                .allowMainThreadQueries()
                .build();
        mContentResolver = InstrumentationRegistry.getContext().getContentResolver();
    }

    @Test
    public void getImmosWhenNoImmoInserted() {
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(ImmoContentProvider.URI_ITEM, AGENT_ID), null, null, null, null);
        assertThat(cursor, notNullValue());
        cursor.close();
    }

    @Test
    public void insertAndGetImmo() {
        // BEFORE : Adding demo immo
        final Uri userUri = mContentResolver.insert(ImmoContentProvider.URI_ITEM, generateItem());

        // TEST
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(ImmoContentProvider.URI_ITEM, AGENT_ID), null, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.moveToLast(), is(true));
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("type")), is("Apartment"));
    }

    private ContentValues generateItem(){
        final ContentValues values = new ContentValues();
        values.put("type", "Apartment");
        values.put("agentId", AGENT_ID);
        return values;
    }

}