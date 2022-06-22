package com.avit.apnamzp.utils;

public class PrettyStrings {

    public static String getPriceInRupees(String cost){
        return "₹" + cost + ".00";
    }

    public static String getPriceInRupees(int cost){
        return "₹" + cost + ".00";
    }

}
