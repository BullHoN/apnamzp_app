package com.avit.apnamzp.localdb;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Printer;

import com.avit.apnamzp.models.order.DeliveryAddress;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

public class User {

    public static String getFCMToken(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefNames.SHAREDDB_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(SharedPrefNames.USER_FCM_TOKEN,"");
    }

    public static void setFCMToken(Context context,String token){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefNames.SHAREDDB_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(SharedPrefNames.USER_FCM_TOKEN,token);
        editor.apply();
    }

    public static String getPhoneNumber(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefNames.SHAREDDB_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(SharedPrefNames.USER_PHONE,null);
    }

    public static void setPhoneNumber(Context context,String phoneNo){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefNames.SHAREDDB_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();

        editor.putString(SharedPrefNames.USER_PHONE,phoneNo);
        editor.apply();
    }

    public static String getUsername(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefNames.SHAREDDB_NAME,Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString(SharedPrefNames.USER_NAME,null);
        return userName;
    }

    public static void setUsername(Context context,String username){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefNames.SHAREDDB_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();

        editor.putString(SharedPrefNames.USER_NAME,username);
        editor.apply();
    }

    public static Boolean getIsVerified(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefNames.SHAREDDB_NAME,Context.MODE_PRIVATE);
        Boolean curr = sharedPreferences.getBoolean(SharedPrefNames.USER_VERIFIED,false);
        return curr;
    }

    public static void setIsVerified(Context context,boolean val){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefNames.SHAREDDB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putBoolean(SharedPrefNames.USER_VERIFIED,val);
        editor.apply();
    }

    public static LatLng getLatLng(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefNames.SHAREDDB_NAME,Context.MODE_PRIVATE);
        String latLangString = sharedPreferences.getString(SharedPrefNames.USER_LATLANG,null);
        if(latLangString == null) return null;

        Gson gson = new Gson();
        return gson.fromJson(latLangString,LatLng.class);
    }

    public static LatLng getSecondaryLatLang(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefNames.SHAREDDB_NAME,Context.MODE_PRIVATE);
        String latLangString = sharedPreferences.getString(SharedPrefNames.USER_SECONDARY_LATLANG,null);
        if(latLangString == null) return null;

        Gson gson = new Gson();
        return gson.fromJson(latLangString,LatLng.class);
    }

    public static void setLatLang(Context context,LatLng latLng){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefNames.SHAREDDB_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String latLangString = gson.toJson(latLng,LatLng.class);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SharedPrefNames.USER_LATLANG,latLangString);
        editor.apply();
    }

    public static void setSecondaryLatLang(Context context,LatLng latLng){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefNames.SHAREDDB_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String latLangString = gson.toJson(latLng,LatLng.class);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SharedPrefNames.USER_SECONDARY_LATLANG,latLangString);
        editor.apply();
    }

    public static String getGoogleMapStreetAddress(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefNames.SHAREDDB_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SharedPrefNames.USER_STREET_ADDRESS,"");
    }

    public static String getSecondaryGoogleMapStreetAddress(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefNames.SHAREDDB_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SharedPrefNames.USER_SECONDARY_STREET_ADDRESS,"");
    }


    public static void setGoogleMapStreetAddress(Context context,String address){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefNames.SHAREDDB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(SharedPrefNames.USER_STREET_ADDRESS,address);
        editor.apply();
    }

    public static void setSecondaryGoogleMapStreetAddress(Context context,String address){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefNames.SHAREDDB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(SharedPrefNames.USER_SECONDARY_STREET_ADDRESS,address);
        editor.apply();
    }

    public static String getHomeDetails(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefNames.SHAREDDB_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SharedPrefNames.USER_HOUSE_NO,"");
    }

    public static String getSecondaryHomeDetails(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefNames.SHAREDDB_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SharedPrefNames.USER_SECONDARY_HOUSE_NO,"");
    }

    public static void setHomeDetails(Context context,String address){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefNames.SHAREDDB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(SharedPrefNames.USER_HOUSE_NO,address);
        editor.apply();
    }

    public static void setSecondaryHomeDetails(Context context,String address){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefNames.SHAREDDB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(SharedPrefNames.USER_SECONDARY_HOUSE_NO,address);
        editor.apply();
    }

    public static String getLandMark(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefNames.SHAREDDB_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SharedPrefNames.USER_LANDMARK,"");
    }

    public static String getSecondaryLandMark(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefNames.SHAREDDB_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SharedPrefNames.USER_SECONDARY_LANDMARK,"");
    }

    public static void setLandMark(Context context,String address){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefNames.SHAREDDB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(SharedPrefNames.USER_LANDMARK,address);
        editor.apply();
    }

    public static void setSecondaryLandMark(Context context,String address){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefNames.SHAREDDB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(SharedPrefNames.USER_SECONDARY_LANDMARK,address);
        editor.apply();
    }

    public static DeliveryAddress getDeliveryAddress(Context context){
        LatLng latLng = User.getLatLng(context);

        return new DeliveryAddress(String.valueOf(latLng.latitude),String.valueOf(latLng.longitude),User.getGoogleMapStreetAddress(context),User.getHomeDetails(context),User.getLandMark(context));
    }

    public static DeliveryAddress getSecondaryDeliveryAddress(Context context){
        LatLng latLng = User.getSecondaryLatLang(context);

        return new DeliveryAddress(String.valueOf(latLng.latitude),String.valueOf(latLng.longitude),User.getSecondaryGoogleMapStreetAddress(context),User.getSecondaryHomeDetails(context),User.getSecondaryLandMark(context));
    }

    public static String getReferAndEarnLink(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefNames.SHAREDDB_NAME, Context.MODE_PRIVATE);

        if(sharedPreferences.contains(SharedPrefNames.USER_REFER_AND_EARN_LINK)){
            return sharedPreferences.getString(SharedPrefNames.USER_REFER_AND_EARN_LINK,"");
        }

        return null;
    }

    public static void setReferAndEarnLink(Context context,String link){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefNames.SHAREDDB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(SharedPrefNames.USER_REFER_AND_EARN_LINK,link);
        editor.apply();
    }

}
