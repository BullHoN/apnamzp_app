package com.avit.apnamzp.models.shop;

public class DeliveryPricings {

    private int BELOW_THREE;
    private int BELOW_SIX;

    public DeliveryPricings(int BELOW_THREE, int BELOW_SIX) {
        this.BELOW_THREE = BELOW_THREE;
        this.BELOW_SIX = BELOW_SIX;
    }

    public int getBELOW_THREE() {
        return BELOW_THREE;
    }

    public int getBELOW_SIX() {
        return BELOW_SIX;
    }
}
