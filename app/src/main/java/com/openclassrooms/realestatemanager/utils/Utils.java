package com.openclassrooms.realestatemanager.utils;

import android.content.Context;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import android.provider.OpenableColumns;
import android.support.v7.app.AlertDialog;

import com.google.android.gms.maps.model.LatLng;
import com.openclassrooms.realestatemanager.models.local.immovables.Vicinity;

import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    public static int getTodayDateInt(){
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyyMMdd");
        return Integer.parseInt(dateFormat.format(currentDate));
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

    public static String convertVicinityToMapsStaticAPIFormat(Vicinity vicinity){
        String addressFormat = vicinity.getAddress().replace(" ", "+");
        String vicinityFormatted = addressFormat + "," + vicinity.getCity();
        return vicinityFormatted;
    }

    public static String getRealFileNameFromURI(Uri uri, Context context) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public static LatLng getLocationFromAddress(Context context, String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng loc = null;
        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null || address.size() == 0) {
                return null;
            }
            Address location = address.get(0);
            loc = new LatLng(location.getLatitude(), location.getLongitude() );
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return loc;
    }

    public static int changeDateFormat(String baseDate){
        String temp = baseDate;
        String dayFormat = temp.substring(0, 2);
        String monthFormat = temp.substring(3, 5);
        String yearFormat = temp.substring(6, 10);
        temp = yearFormat + monthFormat + dayFormat;
        return Integer.parseInt(temp);
    }
}
