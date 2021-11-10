package com.avit.apnamzp.models.order;

import com.avit.apnamzp.models.shop.ShopData;
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
    private BillingDetails billingDetails;
    private ShopData shopData;
    private String _id;
    private int orderStatus;

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setShopData(ShopData shopData) {
        this.shopData = shopData;
    }

    public String get_id() {
        return _id;
    }

    public void setBillingDetails(BillingDetails billingDetails) {
        this.billingDetails = billingDetails;
    }

    public ShopData getShopData() {
        return shopData;
    }

    public OrderItem(){

    }

    public OrderItem(int itemTotal, int totalTaxesAndPackingCharge, int deliveryCharge, int totalDiscount, int totalPay, Boolean isDeliveryService, String specialInstructions, Boolean isPaid, String shopID, String shopCategory, List<ShopItemData> orderItems, String userId, DeliveryAddress deliveryAddress, int offerDiscountedAmount, String offerCode, BillingDetails billingDetails) {
        this.itemTotal = itemTotal;
        this.totalTaxesAndPackingCharge = totalTaxesAndPackingCharge;
        this.deliveryCharge = deliveryCharge;
        this.totalDiscount = totalDiscount;
        this.totalPay = totalPay;
        this.isDeliveryService = isDeliveryService;
        this.specialInstructions = specialInstructions;
        this.isPaid = isPaid;
        this.shopID = shopID;
        this.shopCategory = shopCategory;
        this.orderItems = orderItems;
        this.userId = userId;
        this.deliveryAddress = deliveryAddress;
        this.offerDiscountedAmount = offerDiscountedAmount;
        this.offerCode = offerCode;
        this.billingDetails = billingDetails;
    }

    public BillingDetails getBillingDetails() {
        return billingDetails;
    }

    public void setBillingDetails() {
        this.billingDetails = new BillingDetails(deliveryCharge,itemTotal,offerDiscountedAmount,totalDiscount,totalTaxesAndPackingCharge,totalPay,isDeliveryService);
    }

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
