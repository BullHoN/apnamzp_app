package com.avit.apnamzp.models.cart;

public class CartMetaData {

    private int itemsOnTheWayCost;
    private int slurgeCharges;
    private String slurgeReason;

    public CartMetaData(int itemsOnTheWayCost, int slurgeCharges, String slurgeReason) {
        this.itemsOnTheWayCost = itemsOnTheWayCost;
        this.slurgeCharges = slurgeCharges;
        this.slurgeReason = slurgeReason;
    }

    public CartMetaData(int itemsOnTheWayCost) {
        this.itemsOnTheWayCost = itemsOnTheWayCost;
    }

    public int getSlurgeCharges() {
        return slurgeCharges;
    }

    public String getSlurgeReason() {
        return slurgeReason;
    }

    public int getItemsOnTheWayCost() {
        return itemsOnTheWayCost;
    }
}
