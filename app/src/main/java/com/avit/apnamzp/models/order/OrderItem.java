package com.avit.apnamzp.models.order;

import android.util.Log;

import com.avit.apnamzp.models.shop.ShopData;
import com.avit.apnamzp.models.shop.ShopItemData;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class OrderItem {
    private int itemTotal;
    private int totalTaxesAndPackingCharge;
    private int deliveryCharge;
    private int totalDiscount;
    private int totalPay;
    private int itemOnTheWaySingleCost;

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
    @SerializedName("created_at")
    private Date createdAt;
    private int orderType;
    private String cancelReason;
    private String assignedDeliveryBoy;
    private List<String> itemsOnTheWay;
    private String actualDistance;
    private boolean itemsOnTheWayCancelled;
    private boolean adminShopService;
    private boolean userFeedBack;
    private static String TAG = "OrderItem";
    private String paymentId;

    public OrderItem(int itemTotal, int totalTaxesAndPackingCharge, int deliveryCharge, int totalDiscount, int totalPay, int itemOnTheWaySingleCost, Boolean isDeliveryService, String specialInstructions, Boolean isPaid, String shopID, String shopCategory, List<ShopItemData> orderItems, String userId, DeliveryAddress deliveryAddress, int offerDiscountedAmount, String offerCode, BillingDetails billingDetails, ShopData shopData, String _id, int orderStatus, Date createdAt, int orderType, String cancelReason, String assignedDeliveryBoy, List<String> itemsOnTheWay, String actualDistance, boolean itemsOnTheWayCancelled, boolean adminShopService, boolean userFeedBack, String paymentId) {
        this.itemTotal = itemTotal;
        this.totalTaxesAndPackingCharge = totalTaxesAndPackingCharge;
        this.deliveryCharge = deliveryCharge;
        this.totalDiscount = totalDiscount;
        this.totalPay = totalPay;
        this.itemOnTheWaySingleCost = itemOnTheWaySingleCost;
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
        this.shopData = shopData;
        this._id = _id;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.orderType = orderType;
        this.cancelReason = cancelReason;
        this.assignedDeliveryBoy = assignedDeliveryBoy;
        this.itemsOnTheWay = itemsOnTheWay;
        this.actualDistance = actualDistance;
        this.itemsOnTheWayCancelled = itemsOnTheWayCancelled;
        this.adminShopService = adminShopService;
        this.userFeedBack = userFeedBack;
        this.paymentId = paymentId;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public OrderItem(int itemTotal, int totalTaxesAndPackingCharge, int deliveryCharge, int totalDiscount, int totalPay, int itemOnTheWaySingleCost, Boolean isDeliveryService, String specialInstructions, Boolean isPaid, String shopID, String shopCategory, List<ShopItemData> orderItems, String userId, DeliveryAddress deliveryAddress, int offerDiscountedAmount, String offerCode, BillingDetails billingDetails, ShopData shopData, String _id, int orderStatus, Date createdAt, int orderType, String cancelReason, String assignedDeliveryBoy, List<String> itemsOnTheWay, String actualDistance, boolean itemsOnTheWayCancelled, boolean adminShopService, boolean userFeedBack) {
        this.itemTotal = itemTotal;
        this.totalTaxesAndPackingCharge = totalTaxesAndPackingCharge;
        this.deliveryCharge = deliveryCharge;
        this.totalDiscount = totalDiscount;
        this.totalPay = totalPay;
        this.itemOnTheWaySingleCost = itemOnTheWaySingleCost;
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
        this.shopData = shopData;
        this._id = _id;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.orderType = orderType;
        this.cancelReason = cancelReason;
        this.assignedDeliveryBoy = assignedDeliveryBoy;
        this.itemsOnTheWay = itemsOnTheWay;
        this.actualDistance = actualDistance;
        this.itemsOnTheWayCancelled = itemsOnTheWayCancelled;
        this.adminShopService = adminShopService;
        this.userFeedBack = userFeedBack;
    }

    public OrderItem(int itemTotal, int totalTaxesAndPackingCharge, int deliveryCharge, int totalDiscount, int totalPay, int itemOnTheWaySingleCost, Boolean isDeliveryService, String specialInstructions, Boolean isPaid, String shopID, String shopCategory, List<ShopItemData> orderItems, String userId, DeliveryAddress deliveryAddress, int offerDiscountedAmount, String offerCode, BillingDetails billingDetails, ShopData shopData, String _id, int orderStatus, Date createdAt, int orderType, String cancelReason, String assignedDeliveryBoy, List<String> itemsOnTheWay, String actualDistance, boolean itemsOnTheWayCancelled, boolean adminShopService) {
        this.itemTotal = itemTotal;
        this.totalTaxesAndPackingCharge = totalTaxesAndPackingCharge;
        this.deliveryCharge = deliveryCharge;
        this.totalDiscount = totalDiscount;
        this.totalPay = totalPay;
        this.itemOnTheWaySingleCost = itemOnTheWaySingleCost;
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
        this.shopData = shopData;
        this._id = _id;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.orderType = orderType;
        this.cancelReason = cancelReason;
        this.assignedDeliveryBoy = assignedDeliveryBoy;
        this.itemsOnTheWay = itemsOnTheWay;
        this.actualDistance = actualDistance;
        this.itemsOnTheWayCancelled = itemsOnTheWayCancelled;
        this.adminShopService = adminShopService;
    }

    public OrderItem(int itemTotal, int totalTaxesAndPackingCharge, int deliveryCharge, int totalDiscount, int totalPay, int itemOnTheWaySingleCost, Boolean isDeliveryService, String specialInstructions, Boolean isPaid, String shopID, String shopCategory, List<ShopItemData> orderItems, String userId, DeliveryAddress deliveryAddress, int offerDiscountedAmount, String offerCode, BillingDetails billingDetails, ShopData shopData, String _id, int orderStatus, Date createdAt, int orderType, String cancelReason, String assignedDeliveryBoy, List<String> itemsOnTheWay, String actualDistance, boolean itemsOnTheWayCancelled) {
        this.itemTotal = itemTotal;
        this.totalTaxesAndPackingCharge = totalTaxesAndPackingCharge;
        this.deliveryCharge = deliveryCharge;
        this.totalDiscount = totalDiscount;
        this.totalPay = totalPay;
        this.itemOnTheWaySingleCost = itemOnTheWaySingleCost;
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
        this.shopData = shopData;
        this._id = _id;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.orderType = orderType;
        this.cancelReason = cancelReason;
        this.assignedDeliveryBoy = assignedDeliveryBoy;
        this.itemsOnTheWay = itemsOnTheWay;
        this.actualDistance = actualDistance;
        this.itemsOnTheWayCancelled = itemsOnTheWayCancelled;
    }

    public OrderItem(int itemTotal, int totalTaxesAndPackingCharge, int deliveryCharge, int totalDiscount, int totalPay, int itemOnTheWaySingleCost, Boolean isDeliveryService, String specialInstructions, Boolean isPaid, String shopID, String shopCategory, List<ShopItemData> orderItems, String userId, DeliveryAddress deliveryAddress, int offerDiscountedAmount, String offerCode, BillingDetails billingDetails, ShopData shopData, String _id, int orderStatus, Date createdAt, int orderType, String cancelReason, String assignedDeliveryBoy, List<String> itemsOnTheWay, String actualDistance) {
        this.itemTotal = itemTotal;
        this.totalTaxesAndPackingCharge = totalTaxesAndPackingCharge;
        this.deliveryCharge = deliveryCharge;
        this.totalDiscount = totalDiscount;
        this.totalPay = totalPay;
        this.itemOnTheWaySingleCost = itemOnTheWaySingleCost;
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
        this.shopData = shopData;
        this._id = _id;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.orderType = orderType;
        this.cancelReason = cancelReason;
        this.assignedDeliveryBoy = assignedDeliveryBoy;
        this.itemsOnTheWay = itemsOnTheWay;
        this.actualDistance = actualDistance;
    }

    public OrderItem(int itemTotal, int totalTaxesAndPackingCharge, int deliveryCharge, int totalDiscount, int totalPay, int itemOnTheWaySingleCost, Boolean isDeliveryService, String specialInstructions, Boolean isPaid, String shopID, String shopCategory, List<ShopItemData> orderItems, String userId, DeliveryAddress deliveryAddress, int offerDiscountedAmount, String offerCode, BillingDetails billingDetails, ShopData shopData, String _id, int orderStatus, Date createdAt, int orderType, String cancelReason, String assignedDeliveryBoy, List<String> itemsOnTheWay) {
        this.itemTotal = itemTotal;
        this.totalTaxesAndPackingCharge = totalTaxesAndPackingCharge;
        this.deliveryCharge = deliveryCharge;
        this.totalDiscount = totalDiscount;
        this.totalPay = totalPay;
        this.itemOnTheWaySingleCost = itemOnTheWaySingleCost;
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
        this.shopData = shopData;
        this._id = _id;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.orderType = orderType;
        this.cancelReason = cancelReason;
        this.assignedDeliveryBoy = assignedDeliveryBoy;
        this.itemsOnTheWay = itemsOnTheWay;
    }

    public OrderItem(int itemTotal, int totalTaxesAndPackingCharge, int deliveryCharge, int totalDiscount, int totalPay, Boolean isDeliveryService, String specialInstructions, Boolean isPaid, String shopID, String shopCategory, List<ShopItemData> orderItems, String userId, DeliveryAddress deliveryAddress, int offerDiscountedAmount, String offerCode, BillingDetails billingDetails, ShopData shopData, String _id, int orderStatus, Date createdAt, int orderType, String cancelReason, String assignedDeliveryBoy) {
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
        this.shopData = shopData;
        this._id = _id;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.orderType = orderType;
        this.cancelReason = cancelReason;
        this.assignedDeliveryBoy = assignedDeliveryBoy;
    }

    public String getAssignedDeliveryBoy() {
        return assignedDeliveryBoy;
    }

    public OrderItem(int itemTotal, int totalTaxesAndPackingCharge, int deliveryCharge, int totalDiscount, int totalPay, Boolean isDeliveryService, String specialInstructions, Boolean isPaid, String shopID, String shopCategory, List<ShopItemData> orderItems, String userId, DeliveryAddress deliveryAddress, int offerDiscountedAmount, String offerCode, BillingDetails billingDetails, ShopData shopData, String _id, int orderStatus, Date createdAt, int orderType, String cancelReason) {
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
        this.shopData = shopData;
        this._id = _id;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.orderType = orderType;
        this.cancelReason = cancelReason;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public boolean isUserFeedBack() {
        return userFeedBack;
    }

    public void setAdminShopService(boolean adminShopService) {
        this.adminShopService = adminShopService;
    }

    public boolean isAdminShopService() {
        return adminShopService;
    }

    public boolean isItemsOnTheWayCancelled() {
        return itemsOnTheWayCancelled;
    }

    public void setActualDistance(String actualDistance) {
        this.actualDistance = actualDistance;
    }

    public String getActualDistance() {
        return actualDistance;
    }

    public int getItemOnTheWaySingleCost() {
        return itemOnTheWaySingleCost;
    }

    public void setItemOnTheWaySingleCost(int itemOnTheWaySingleCost) {
        this.itemOnTheWaySingleCost = itemOnTheWaySingleCost;
    }

    public void setItemsOnTheWay(List<String> itemsOnTheWay) {
        this.itemsOnTheWay = itemsOnTheWay;
    }

    public List<String> getItemsOnTheWay() {
        return itemsOnTheWay;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

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

    public void setBillingDetails(String taxPercentage) {
        this.billingDetails = new BillingDetails(deliveryCharge,itemTotal,offerDiscountedAmount,totalDiscount,totalTaxesAndPackingCharge,totalPay,getTotalFromItemsOnTheWay(),taxPercentage,isDeliveryService);
        this.billingDetails.setFreeDeliveryPrice(Integer.parseInt(getShopData().getPricingDetails().getMinFreeDeliveryPrice()));
    }

    public int calculateTotalPrice(){
        if(isDeliveryService){
            totalPay = itemTotal + totalTaxesAndPackingCharge + deliveryCharge - totalDiscount - offerDiscountedAmount + getTotalFromItemsOnTheWay();
            if(itemTotal >= Integer.parseInt(getShopData().getPricingDetails().getMinFreeDeliveryPrice())){
                totalPay -= deliveryCharge;
            }
            return totalPay;
        }
        totalPay = itemTotal + totalTaxesAndPackingCharge - totalDiscount - offerDiscountedAmount;
        return totalPay;
    }

    public int getTotalFromItemsOnTheWay(){
        if(itemsOnTheWay == null) return 0;
        return itemsOnTheWay.size() * itemOnTheWaySingleCost;
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
//        Log.i(TAG, "getDeliveryCharge: " + itemTotal + " " + Integer.parseInt(getShopData().getPricingDetails().getMinFreeDeliveryPrice()));
        if(itemTotal >= Integer.parseInt(getShopData().getPricingDetails().getMinFreeDeliveryPrice())){
            return 0;
        }
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
