package com.openclassrooms.realestatemanager;

import android.test.ActivityInstrumentationTestCase2;

import com.openclassrooms.realestatemanager.controllers.activities.MainActivity;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.robotium.solo.Solo;



import static android.support.test.InstrumentationRegistry.getTargetContext;


public class InternetConnexionTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;

    public InternetConnexionTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    protected void tearDown(){
        solo.finishOpenedActivities();
    }

    public void testWifiConnexionTest(){
        // Test
        solo.setWiFiData(true);
        solo.waitForActivity(String.valueOf(solo.getCurrentActivity()), 5000);
        Boolean internet = Utils.isInternetAvailable(getTargetContext());
        assertTrue(internet);
        solo.setWiFiData(false);
    }

}
