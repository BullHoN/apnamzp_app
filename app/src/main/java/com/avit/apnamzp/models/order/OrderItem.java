package com.avit.apnamzp.models.order;

import com.avit.apnamzp.models.shop.ShopItemData;

import java.util.List;

public class OrderItem {
    private int itemTotal;
    private int totalTaxesAndPackingCharge;
    private int deliveryCharge;
    private int totalDiscount;
    private int totalPay;
    private Boolean isDeliveryService;
    private String specialInstructions;
    private Boolean isPaid;

    private String shopID;
    private String shopCategory;
    private List<ShopItemData> orderItems;

    private String userId;
    private DeliveryAddress deliveryAddress;

    private int offerDiscountedAmount;
    private String offerCode;

    public int calculateTotalPrice(){
        if(isDeliveryService){
            totalPay = itemTotal + totalTaxesAndPackingCharge + deliveryCharge - totalDiscount - offerDiscountedAmount;
            return totalPay;
        }
        totalPay = itemTotal + totalTaxesAndPackingCharge - totalDiscount - offerDiscountedAmount;
        return totalPay;
    }

    public void setOfferDiscountedAmount(int offerDiscountedAmount) {
        this.offerDiscountedAmount = offerDiscountedAmount;
    }

    public void setOfferCode(String offerCode) {
        this.offerCode = offerCode;
    }

    public int getOfferDiscountedAmount() {
        return offerDiscountedAmount;
    }

    public String getOfferCode() {
        return offerCode;
    }

    public void setItemTotal(int itemTotal) {
        this.itemTotal = itemTotal;
    }

    public void setTotalTaxesAndPackingCharge(int totalTaxesAndPackingCharge) {
        this.totalTaxesAndPackingCharge = totalTaxesAndPackingCharge;
    }

    public int getDiscountWithOffer(){
        return offerDiscountedAmount + totalDiscount;
    }

    public void setDeliveryCharge(int deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public void setTotalDiscount(int totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public void setTotalPay(int totalPay) {
        this.totalPay = totalPay;
    }

    public void setDeliveryService(Boolean deliveryService) {
        isDeliveryService = deliveryService;
    }

    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public void setShopCategory(String shopCategory) {
        this.shopCategory = shopCategory;
    }

    public void setOrderItems(List<ShopItemData> orderItems) {
        this.orderItems = orderItems;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public int getItemTotal() {
        return itemTotal;
    }

    public int getTotalTaxesAndPackingCharge() {
        return totalTaxesAndPackingCharge;
    }

    public int getDeliveryCharge() {
        return deliveryCharge;
    }

    public int getTotalDiscount() {
        return totalDiscount;
    }

    public int getTotalPay() {
        return totalPay;
    }

    public Boolean getDeliveryService() {
        return isDeliveryService;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public String getShopID() {
        return shopID;
    }

    public String getShopCategory() {
        return shopCategory;
    }

    public List<ShopItemData> getOrderItems() {
        return orderItems;
    }

    public String getUserId() {
        return userId;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }
}
