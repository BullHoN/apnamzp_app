package com.avit.apnamzp.models.order;

public class BillingDetails {
    private int  deliveryCharge, itemTotal, offerDiscountedAmount, totalDiscount, totalTaxesAndPackingCharge, totalPay, itemsOnTheWayTotalCost;
    private Boolean isDeliveryService;

    public BillingDetails(int deliveryCharge, int itemTotal, int offerDiscountedAmount, int totalDiscount, int totalTaxesAndPackingCharge, int totalPay, int itemsOnTheWayTotalCost, Boolean isDeliveryService) {
        this.deliveryCharge = deliveryCharge;
        this.itemTotal = itemTotal;
        this.offerDiscountedAmount = offerDiscountedAmount;
        this.totalDiscount = totalDiscount;
        this.totalTaxesAndPackingCharge = totalTaxesAndPackingCharge;
        this.totalPay = totalPay;
        this.itemsOnTheWayTotalCost = itemsOnTheWayTotalCost;
        this.isDeliveryService = isDeliveryService;
    }

    public BillingDetails(int deliveryCharge, int itemTotal, int offerDiscountedAmount, int totalDiscount, int totalTaxesAndPackingCharge, int totalPay, Boolean isDeliveryService) {
        this.deliveryCharge = deliveryCharge;
        this.itemTotal = itemTotal;
        this.offerDiscountedAmount = offerDiscountedAmount;
        this.totalDiscount = totalDiscount;
        this.totalTaxesAndPackingCharge = totalTaxesAndPackingCharge;
        this.totalPay = totalPay;
        this.isDeliveryService = isDeliveryService;
    }

    public int getItemsOnTheWayTotalCost() {
        return itemsOnTheWayTotalCost;
    }

    public int getDeliveryCharge() {
        return deliveryCharge;
    }

    public int getItemTotal() {
        return itemTotal;
    }

    public int getOfferDiscountedAmount() {
        return offerDiscountedAmount;
    }

    public int getTotalDiscount() {
        return totalDiscount;
    }

    public int getTotalTaxesAndPackingCharge() {
        return totalTaxesAndPackingCharge;
    }

    public int getTotalPay() {
        return totalPay;
    }

    public Boolean getDeliveryService() {
        return isDeliveryService;
    }
}
