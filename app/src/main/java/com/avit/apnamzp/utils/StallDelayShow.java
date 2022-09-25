package com.avit.apnamzp.utils;

import java.util.HashSet;

public class StallDelayShow {
    static HashSet<String> alreadyDisplayed;

    public static boolean isDisplayed(String shopName){
        if(alreadyDisplayed == null){
            alreadyDisplayed = new HashSet<>();
        }

        if(alreadyDisplayed.contains(shopName)){
            return true;
        }

        alreadyDisplayed.add(shopName);
        return false;
    }

}
