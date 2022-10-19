package com.avit.apnamzp.utils;

import java.util.HashSet;

public class HomeDisplayAnimation {
    static boolean alreadyDisplayed = false;

    public static boolean isDisplayed(){
        if(alreadyDisplayed) return true;

        alreadyDisplayed = true;
        return false;
    }
}
