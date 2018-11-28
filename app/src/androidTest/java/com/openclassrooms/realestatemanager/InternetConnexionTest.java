package com.openclassrooms.realestatemanager;

import android.content.Context;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;


import com.openclassrooms.realestatemanager.utils.Utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class InternetConnexionTest {

    private Context context;;

    @Before
    public void setup(){
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void InternetConnexionTest(){
        // Test
        Boolean data = Utils.isInternetAvailable(context);
        assertTrue(data);
    }

}
