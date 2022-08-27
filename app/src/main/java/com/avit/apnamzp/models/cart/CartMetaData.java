package com.avit.apnamzp.models.cart;

import com.avit.apnamzp.models.order.ProcessingFee;

public class CartMetaData {

    private int itemsOnTheWayCost;
    private int slurgeCharges;
    private String slurgeReason;
    private ProcessingFee processingFee;

    public CartMetaData(int itemsOnTheWayCost, int slurgeCharges, String slurgeReason, ProcessingFee processingFee) {
        this.itemsOnTheWayCost = itemsOnTheWayCost;
        this.slurgeCharges = slurgeCharges;
        this.slurgeReason = slurgeReason;
        this.processingFee = processingFee;
    }

    public CartMetaData(int itemsOnTheWayCost, int slurgeCharges, String slurgeReason) {
        this.itemsOnTheWayCost = itemsOnTheWayCost;
        this.slurgeCharges = slurgeCharges;
        this.slurgeReason = slurgeReason;
    }

    public ProcessingFee getProcessingFee() {
        return processingFee;
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
