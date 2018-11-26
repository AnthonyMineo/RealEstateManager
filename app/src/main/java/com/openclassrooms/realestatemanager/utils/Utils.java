package com.openclassrooms.realestatemanager.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Philippe on 21/02/2018.
 */

public class Utils {
    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param dollars
     * @return
     */
    public static int convertDollarToEuro(int dollars, String exchangeRate){
        double exchangeRateFormated = Double.parseDouble(exchangeRate);
        return (int) Math.round(dollars * exchangeRateFormated);
    }

    /**
     * Conversion d'un prix d'un bien immobilier (Euros vers Dollars)
     */
    public static int convertEuroToDollar(int euros, String exchangeRate) {
        double exchangeRateFormated = Double.parseDouble(exchangeRate);
        return (int) Math.round(euros * exchangeRateFormated); }

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @return
     */
    // I had just changed the pattern
    public static String getTodayDate(){
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(currentDate);
    }

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param context
     * @return
     */

    public static Boolean isInternetAvailable(Context context){
        Boolean connectivity = false;
        if(isWifiAvailable(context) || isMobileDataAvailable(context)){
            connectivity = true;
        }
        return connectivity;
    }

    public static Boolean isWifiAvailable(Context context){
        Boolean wifi = false;
        ConnectivityManager cM = (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cM.getActiveNetworkInfo();
        if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()){
            wifi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
        }
        return wifi;
    }

    public static Boolean isMobileDataAvailable(Context context){
        Boolean data = false;
        ConnectivityManager cM = (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cM.getActiveNetworkInfo();
        if(activeNetwork != null && activeNetwork.isConnectedOrConnecting()){
            data = activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
        }
        return data;
    }

}
