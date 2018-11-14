package com.openclassrooms.realestatemanager;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.realestatemanager.models.Agent;
import com.openclassrooms.realestatemanager.models.Immo;
import com.openclassrooms.realestatemanager.models.database.RealEstateDB;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ImmoDaoTest {

    // --- FOR DATA ---
    private RealEstateDB database;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                RealEstateDB.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }

    // --- DATA SET FOR TEST ---
    private static int AGENT_ID = 1;
    private static Agent AGENT_DEMO = new Agent(AGENT_ID, "Anthony", "anthony.mineo.pro@gmail.com", "none");

    private static Immo IMMO_1 = new Immo("Apartment",  210000, 70, 5, "10 rue du test, 30000 Android", false, "14/11/2018", AGENT_ID);
    private static Immo IMMO_2 = new Immo("Mansion",  832500, 234, 16, "20 rue du test, 30000 Android", false, "14/11/2018", AGENT_ID);
    private static Immo IMMO_3 = new Immo("Apartment",  2350000, 79, 6, "13 rue du test, 30000 Android", false, "14/11/2018", AGENT_ID);

    @Test
    public void insertAndGetUser() throws InterruptedException {
        // BEFORE : Adding a new user
        this.database.agentDao().createAgent(AGENT_DEMO);
        // TEST
        Agent agentTest = LiveDataTestUtil.getValue(this.database.agentDao().getAgent(AGENT_ID));
        assertTrue(agentTest.getUserName().equals(AGENT_DEMO.getUserName()) && agentTest.getId() == AGENT_ID);
    }

    @Test
    public void getImmoWhenNoImmoInserted() throws InterruptedException {
        // TEST
        List<Immo> immoList = LiveDataTestUtil.getValue(this.database.immoDao().getImmosByAgent(AGENT_ID));
        assertTrue(immoList.isEmpty());
    }

    @Test
    public void insertAndGetImmo() throws InterruptedException {
        // BEFORE : Adding demo user & demo items
        this.database.agentDao().createAgent(AGENT_DEMO);
        this.database.immoDao().insertImmo(IMMO_1);
        this.database.immoDao().insertImmo(IMMO_2);
        this.database.immoDao().insertImmo(IMMO_3);

        // TEST
        List<Immo> immoList = LiveDataTestUtil.getValue(this.database.immoDao().getImmosByAgent(AGENT_ID));
        assertTrue(immoList.size() == 3);
    }

    @Test
    public void insertAndUpdateItem() throws InterruptedException {
        // BEFORE : Adding demo user & demo items. Next, update item added & re-save it
        this.database.agentDao().createAgent(AGENT_DEMO);
        this.database.immoDao().insertImmo(IMMO_1);
        Immo immoAdded = LiveDataTestUtil.getValue(this.database.immoDao().getImmosByAgent(AGENT_ID)).get(0);

        // Adding a map of path and file name for picture
        Map<String, String> pAFNFP= new HashMap<String, String>();
        pAFNFP.put("path/1", "file.1");
        pAFNFP.put("path/2", "file.2");
        // Adding a list of point of interest
        List<String> pOI = new ArrayList<String>();
        pOI.add("Ecoles");
        pOI.add("SuperMarché");

        immoAdded.setPointOfInterest(pOI);
        immoAdded.setPathAndFileNameForPicture(pAFNFP);
        this.database.immoDao().updateImmo(immoAdded);

        // TEST
        List<Immo> immoList = LiveDataTestUtil.getValue(this.database.immoDao().getImmosByAgent(AGENT_ID));
        assertTrue(immoList.size() == 1);
        assertTrue(immoList.get(0).getPointOfInterest().get(0).contains("Ecoles") && immoList.get(0).getPointOfInterest().get(1).contains("SuperMarché"));
        assertTrue( immoList.get(0).getPathAndFileNameForPicture().containsKey("path/1") && immoList.get(0).getPathAndFileNameForPicture().containsKey("path/2"));
        assertTrue( immoList.get(0).getPathAndFileNameForPicture().containsValue("file.1") && immoList.get(0).getPathAndFileNameForPicture().containsValue("file.2"));
    }

    @Test
    public void insertAndDeleteItem() throws InterruptedException {
        // BEFORE : Adding demo user & demo item. Next, get the item added & delete it.
        this.database.agentDao().createAgent(AGENT_DEMO);
        this.database.immoDao().insertImmo(IMMO_1);
        Immo immoAdded = LiveDataTestUtil.getValue(this.database.immoDao().getImmosByAgent(AGENT_ID)).get(0);
        this.database.immoDao().deleteImmo(immoAdded.getId());

        // TEST
        List<Immo> immoList = LiveDataTestUtil.getValue(this.database.immoDao().getImmosByAgent(AGENT_ID));
        assertTrue(immoList.isEmpty());
    }
}
