package com.openclassrooms.realestatemanager;

import com.openclassrooms.realestatemanager.utils.Utils;


import org.junit.Test;

import static junit.framework.Assert.assertEquals;


public class UtilsTest {

    @Test
    public void CurrencyTest(){
        // - Currency conversion
        int dollarToEuro = Utils.convertDollarToEuro(100, "0.83");
        int euroToDollar = Utils.convertEuroToDollar(83, "1.205");

        // - Currency Test
        assertEquals(83, dollarToEuro);
        assertEquals(100, euroToDollar);
    }

    @Test
    public void DateFormatTest(){
        // - Date format
        String TodayDate = Utils.getTodayDate();
        String dayFormat = TodayDate.substring(0, 2);
        String monthFormat = TodayDate.substring(3, 5);
        String yearFormat = TodayDate.substring(6, 10);
        String separator_1 = TodayDate.substring(2, 3);
        String separator_2 = TodayDate.substring(5, 6);

        // - Date format Test
        assertEquals(2, dayFormat.length());
        assertEquals(2, monthFormat.length());
        assertEquals(4, yearFormat.length());
        assertEquals("/", separator_1);
        assertEquals("/", separator_2);
    }
}
