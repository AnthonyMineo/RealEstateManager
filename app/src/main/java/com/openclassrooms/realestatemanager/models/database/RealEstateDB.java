package com.openclassrooms.realestatemanager.models.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.openclassrooms.realestatemanager.models.Agent;
import com.openclassrooms.realestatemanager.models.Immo;
import com.openclassrooms.realestatemanager.models.database.dao.AgentDao;
import com.openclassrooms.realestatemanager.models.database.dao.ImmoDao;

@Database(entities = {Immo.class, Agent.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class RealEstateDB extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile RealEstateDB INSTANCE;

    // --- DAO
    public abstract ImmoDao immoDao();
    public abstract AgentDao agentDao();


    // --- INSTANCE ---
    public static RealEstateDB getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (RealEstateDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RealEstateDB.class, "MyDatabase.db")
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback prepopulateDatabase(){
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues contentValues = new ContentValues();
                contentValues.put("id", 1);
                contentValues.put("userName", "Anthony");
                contentValues.put("mailAddress", "anthony.mineo.pro@gmail.com");
                contentValues.put("urlPicture", "none");

                db.insert("Agent", OnConflictStrategy.IGNORE, contentValues);
            }
        };
    }
}
