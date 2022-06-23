package com.avit.apnamzp.models.order;

public class BillingDetails {
    private int  deliveryCharge, itemTotal, offerDiscountedAmount, totalDiscount, totalTaxesAndPackingCharge, totalPay, itemsOnTheWayTotalCost;
    private int itemsOnTheWayActualCost;
    private String taxPercentage;
    private Boolean isDeliveryService;

    public BillingDetails(int deliveryCharge, int itemTotal, int offerDiscountedAmount, int totalDiscount, int totalTaxesAndPackingCharge, int totalPay, int itemsOnTheWayTotalCost, int itemsOnTheWayActualCost, String taxPercentage, Boolean isDeliveryService) {
        this.deliveryCharge = deliveryCharge;
        this.itemTotal = itemTotal;
        this.offerDiscountedAmount = offerDiscountedAmount;
        this.totalDiscount = totalDiscount;
        this.totalTaxesAndPackingCharge = totalTaxesAndPackingCharge;
        this.totalPay = totalPay;
        this.itemsOnTheWayTotalCost = itemsOnTheWayTotalCost;
        this.itemsOnTheWayActualCost = itemsOnTheWayActualCost;
        this.taxPercentage = taxPercentage;
        this.isDeliveryService = isDeliveryService;
    }

    public BillingDetails(int deliveryCharge, int itemTotal, int offerDiscountedAmount, int totalDiscount, int totalTaxesAndPackingCharge, int totalPay, int itemsOnTheWayTotalCost, String taxPercentage, Boolean isDeliveryService) {
        this.deliveryCharge = deliveryCharge;
        this.itemTotal = itemTotal;
        this.offerDiscountedAmount = offerDiscountedAmount;
        this.totalDiscount = totalDiscount;
        this.totalTaxesAndPackingCharge = totalTaxesAndPackingCharge;
        this.totalPay = totalPay;
        this.itemsOnTheWayTotalCost = itemsOnTheWayTotalCost;
        this.taxPercentage = taxPercentage;
        this.isDeliveryService = isDeliveryService;
    }

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

    public int getItemsOnTheWayActualCost() {
        return itemsOnTheWayActualCost;
    }

    public String getTaxPercentage() {
        return taxPercentage;
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
