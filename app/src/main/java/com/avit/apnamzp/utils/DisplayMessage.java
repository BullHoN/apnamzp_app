package com.avit.apnamzp.utils;

import android.content.Context;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class DisplayMessage {

    public static void successMessage(Context context,String message,int duration){
        try {
            Toasty.success(context,message,duration)
                    .show();
        }
        catch(Exception e){
            Toast.makeText(context,message,duration)
                    .show();
        }
    }

    public static void errorMessage(Context context,String message,int duration){
        try {
            Toasty.error(context,message,duration)
                    .show();
        }
        catch(Exception e){
            Toast.makeText(context,message,duration)
                    .show();
        }
    }

    public static void infoMessage(Context context,String message,int duration){
        try {
            Toasty.info(context,message,duration)
                    .show();
        }
        catch(Exception e){
            Toast.makeText(context,message,duration)
                    .show();
        }
    }

    public static void warningMessage(Context context,String message,int duration){
        try {
            Toasty.warning(context,message,duration)
                    .show();
        }
        catch(Exception e){
            Toast.makeText(context,message,duration)
                    .show();
        }
    }

    public static void normalMessage(Context context,String message,int duration){
        try {
            Toasty.normal(context,message,duration)
                    .show();
        }
        catch(Exception e){
            Toast.makeText(context,message,duration)
                    .show();
        }
    }

}
