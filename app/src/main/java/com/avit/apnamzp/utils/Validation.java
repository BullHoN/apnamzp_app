package com.avit.apnamzp.utils;

public class Validation {
    public static boolean validateNormalData(String data){
        if(data == null || data.length() < 3){
            return false;
        }

        return true;
    }

    public static boolean validatePhoneNo(String phoneNo){
        if(phoneNo == null || phoneNo.length() != 10){
            return false;
        }

        for(int i=0;i<phoneNo.length();i++){
            if(!("1234567890".contains(String.valueOf(phoneNo.charAt(i))))){
                return false;
            }
        }

        return true;
    }

}
