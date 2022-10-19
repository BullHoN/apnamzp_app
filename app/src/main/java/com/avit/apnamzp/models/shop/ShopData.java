package com.avit.apnamzp.models.shop;

import com.avit.apnamzp.models.order.ProcessingFee;
import com.avit.apnamzp.models.shop.ShopAddressData;
import com.avit.apnamzp.models.shop.ShopCostData;

public class ShopData {
    private String name;
    private String tagLine;
    private ShopCostData pricingDetails;
    private ShopAddressData addressData;
    private Boolean isOpen;
    private String averageDeliveryTime;
    private String bannerImage;
    private String averageRatings;
    private String shopType;
    private String reviews;
    private String menuItemsID;
    private String taxPercentage;
    private String _id;
    private boolean allowCheckout;
    private boolean adminShopService;
    private int increaseDisplayPricePercentage;
    private String fssaiCode;
    private boolean allowProcessingFees;
    private String shopTimings;
    private boolean allowCOD;
    private boolean allowSelfPickup;
    private ProcessingFee processingFees;

    public ShopData(String name, String tagLine, ShopCostData pricingDetails, ShopAddressData addressData, Boolean isOpen, String averageDeliveryTime, String bannerImage, String averageRatings, String shopType, String reviews, String menuItemsID, String taxPercentage, String _id, boolean allowCheckout, boolean adminShopService, int increaseDisplayPricePercentage, String fssaiCode, boolean allowProcessingFees, String shopTimings, boolean allowCOD, boolean allowSelfPickup, ProcessingFee processingFees) {
        this.name = name;
        this.tagLine = tagLine;
        this.pricingDetails = pricingDetails;
        this.addressData = addressData;
        this.isOpen = isOpen;
        this.averageDeliveryTime = averageDeliveryTime;
        this.bannerImage = bannerImage;
        this.averageRatings = averageRatings;
        this.shopType = shopType;
        this.reviews = reviews;
        this.menuItemsID = menuItemsID;
        this.taxPercentage = taxPercentage;
        this._id = _id;
        this.allowCheckout = allowCheckout;
        this.adminShopService = adminShopService;
        this.increaseDisplayPricePercentage = increaseDisplayPricePercentage;
        this.fssaiCode = fssaiCode;
        this.allowProcessingFees = allowProcessingFees;
        this.shopTimings = shopTimings;
        this.allowCOD = allowCOD;
        this.allowSelfPickup = allowSelfPickup;
        this.processingFees = processingFees;
    }

    public ShopData(ShopData shopData) {
        this.name = shopData.name;
        this.tagLine = shopData.tagLine;
        this.pricingDetails = shopData.pricingDetails;
        this.addressData = shopData.addressData;
        this.isOpen = shopData.isOpen;
        this.averageDeliveryTime = shopData.averageDeliveryTime;
        this.bannerImage = shopData.bannerImage;
        this.averageRatings = shopData.averageRatings;
        this.shopType = shopData.shopType;
        this.reviews = shopData.reviews;
        this.menuItemsID = shopData.menuItemsID;
        this.taxPercentage = shopData.taxPercentage;
        this._id = shopData._id;
        this.allowCheckout = shopData.allowCheckout;
        this.adminShopService = shopData.adminShopService;
        this.increaseDisplayPricePercentage = shopData.increaseDisplayPricePercentage;
        this.fssaiCode = shopData.fssaiCode;
        this.allowProcessingFees = shopData.allowProcessingFees;
        this.shopTimings = shopData.shopTimings;
        this.allowCOD = shopData.allowCOD;
        this.allowSelfPickup = shopData.allowSelfPickup;
        this.processingFees = shopData.processingFees;
    }

    public ShopData(String name, String tagLine, ShopCostData pricingDetails, ShopAddressData addressData, Boolean isOpen, String averageDeliveryTime, String bannerImage, String averageRatings, String shopType, String reviews, String menuItemsID, String taxPercentage, String _id, boolean allowCheckout, boolean adminShopService, int increaseDisplayPricePercentage, String fssaiCode, boolean allowProcessingFees, String shopTimings, boolean allowCOD, boolean allowSelfPickup) {
        this.name = name;
        this.tagLine = tagLine;
        this.pricingDetails = pricingDetails;
        this.addressData = addressData;
        this.isOpen = isOpen;
        this.averageDeliveryTime = averageDeliveryTime;
        this.bannerImage = bannerImage;
        this.averageRatings = averageRatings;
        this.shopType = shopType;
        this.reviews = reviews;
        this.menuItemsID = menuItemsID;
        this.taxPercentage = taxPercentage;
        this._id = _id;
        this.allowCheckout = allowCheckout;
        this.adminShopService = adminShopService;
        this.increaseDisplayPricePercentage = increaseDisplayPricePercentage;
        this.fssaiCode = fssaiCode;
        this.allowProcessingFees = allowProcessingFees;
        this.shopTimings = shopTimings;
        this.allowCOD = allowCOD;
        this.allowSelfPickup = allowSelfPickup;
    }

    public ShopData(String name, String tagLine, ShopCostData pricingDetails, ShopAddressData addressData, Boolean isOpen, String averageDeliveryTime, String bannerImage, String averageRatings, String shopType, String reviews, String menuItemsID, String taxPercentage, String _id, boolean allowCheckout, boolean adminShopService, int increaseDisplayPricePercentage, String fssaiCode, boolean allowProcessingFees, String shopTimings) {
        this.name = name;
        this.tagLine = tagLine;
        this.pricingDetails = pricingDetails;
        this.addressData = addressData;
        this.isOpen = isOpen;
        this.averageDeliveryTime = averageDeliveryTime;
        this.bannerImage = bannerImage;
        this.averageRatings = averageRatings;
        this.shopType = shopType;
        this.reviews = reviews;
        this.menuItemsID = menuItemsID;
        this.taxPercentage = taxPercentage;
        this._id = _id;
        this.allowCheckout = allowCheckout;
        this.adminShopService = adminShopService;
        this.increaseDisplayPricePercentage = increaseDisplayPricePercentage;
        this.fssaiCode = fssaiCode;
        this.allowProcessingFees = allowProcessingFees;
        this.shopTimings = shopTimings;
    }

    public ShopData(String name, String tagLine, ShopCostData pricingDetails, ShopAddressData addressData, Boolean isOpen, String averageDeliveryTime, String bannerImage, String averageRatings, String shopType, String reviews, String menuItemsID, String taxPercentage, String _id, boolean allowCheckout, boolean adminShopService, int increaseDisplayPricePercentage, String fssaiCode, boolean allowProcessingFees) {
        this.name = name;
        this.tagLine = tagLine;
        this.pricingDetails = pricingDetails;
        this.addressData = addressData;
        this.isOpen = isOpen;
        this.averageDeliveryTime = averageDeliveryTime;
        this.bannerImage = bannerImage;
        this.averageRatings = averageRatings;
        this.shopType = shopType;
        this.reviews = reviews;
        this.menuItemsID = menuItemsID;
        this.taxPercentage = taxPercentage;
        this._id = _id;
        this.allowCheckout = allowCheckout;
        this.adminShopService = adminShopService;
        this.increaseDisplayPricePercentage = increaseDisplayPricePercentage;
        this.fssaiCode = fssaiCode;
        this.allowProcessingFees = allowProcessingFees;
    }

    public ShopData(String name, String tagLine, ShopCostData pricingDetails, ShopAddressData addressData, Boolean isOpen, String averageDeliveryTime, String bannerImage, String averageRatings, String shopType, String reviews, String menuItemsID, String taxPercentage, String _id, boolean allowCheckout, boolean adminShopService, int increaseDisplayPricePercentage, String fssaiCode) {
        this.name = name;
        this.tagLine = tagLine;
        this.pricingDetails = pricingDetails;
        this.addressData = addressData;
        this.isOpen = isOpen;
        this.averageDeliveryTime = averageDeliveryTime;
        this.bannerImage = bannerImage;
        this.averageRatings = averageRatings;
        this.shopType = shopType;
        this.reviews = reviews;
        this.menuItemsID = menuItemsID;
        this.taxPercentage = taxPercentage;
        this._id = _id;
        this.allowCheckout = allowCheckout;
        this.adminShopService = adminShopService;
        this.increaseDisplayPricePercentage = increaseDisplayPricePercentage;
        this.fssaiCode = fssaiCode;
    }

    public ShopData(String name, String tagLine, ShopCostData pricingDetails, ShopAddressData addressData, Boolean isOpen, String averageDeliveryTime, String bannerImage, String averageRatings, String shopType, String reviews, String menuItemsID, String taxPercentage, String _id, boolean allowCheckout, boolean adminShopService, int increaseDisplayPricePercentage) {
        this.name = name;
        this.tagLine = tagLine;
        this.pricingDetails = pricingDetails;
        this.addressData = addressData;
        this.isOpen = isOpen;
        this.averageDeliveryTime = averageDeliveryTime;
        this.bannerImage = bannerImage;
        this.averageRatings = averageRatings;
        this.shopType = shopType;
        this.reviews = reviews;
        this.menuItemsID = menuItemsID;
        this.taxPercentage = taxPercentage;
        this._id = _id;
        this.allowCheckout = allowCheckout;
        this.adminShopService = adminShopService;
        this.increaseDisplayPricePercentage = increaseDisplayPricePercentage;
    }

    public ShopData(String name, String tagLine, ShopCostData pricingDetails, ShopAddressData addressData, Boolean isOpen, String averageDeliveryTime, String bannerImage, String averageRatings, String shopType, String reviews, String menuItemsID, String taxPercentage, String _id, boolean allowCheckout, boolean adminShopService) {
        this.name = name;
        this.tagLine = tagLine;
        this.pricingDetails = pricingDetails;
        this.addressData = addressData;
        this.isOpen = isOpen;
        this.averageDeliveryTime = averageDeliveryTime;
        this.bannerImage = bannerImage;
        this.averageRatings = averageRatings;
        this.shopType = shopType;
        this.reviews = reviews;
        this.menuItemsID = menuItemsID;
        this.taxPercentage = taxPercentage;
        this._id = _id;
        this.allowCheckout = allowCheckout;
        this.adminShopService = adminShopService;
    }

    public ShopData(String name, String tagLine, ShopCostData pricingDetails, ShopAddressData addressData, Boolean isOpen, String averageDeliveryTime, String bannerImage, String averageRatings, String shopType, String reviews, String menuItemsID, String taxPercentage, String _id, boolean allowCheckout) {
        this.name = name;
        this.tagLine = tagLine;
        this.pricingDetails = pricingDetails;
        this.addressData = addressData;
        this.isOpen = isOpen;
        this.averageDeliveryTime = averageDeliveryTime;
        this.bannerImage = bannerImage;
        this.averageRatings = averageRatings;
        this.shopType = shopType;
        this.reviews = reviews;
        this.menuItemsID = menuItemsID;
        this.taxPercentage = taxPercentage;
        this._id = _id;
        this.allowCheckout = allowCheckout;
    }

    public ShopData(String name, String tagLine, ShopCostData pricingDetails, ShopAddressData addressData, Boolean isOpen, String averageDeliveryTime, String bannerImage, String averageRatings, String shopType, String reviews, String menuItemsID, String taxPercentage, String _id) {
        this.name = name;
        this.tagLine = tagLine;
        this.pricingDetails = pricingDetails;
        this.addressData = addressData;
        this.isOpen = isOpen;
        this.averageDeliveryTime = averageDeliveryTime;
        this.bannerImage = bannerImage;
        this.averageRatings = averageRatings;
        this.shopType = shopType;
        this.reviews = reviews;
        this.menuItemsID = menuItemsID;
        this.taxPercentage = taxPercentage;
        this._id = _id;
    }

    public ShopData(String shopName, Boolean isOpen) {
        this.name = shopName;
        this.isOpen = isOpen;
    }

    public ShopData(String shopName, String tagLine, ShopCostData pricingDetails, ShopAddressData addressData, Boolean isOpen, String averageDeliveryTime) {
        this.name = shopName;
        this.tagLine = tagLine;
        this.pricingDetails = pricingDetails;
        this.addressData = addressData;
        this.isOpen = isOpen;
        this.averageDeliveryTime = averageDeliveryTime;
    }

    public ShopData(String name, String tagLine, ShopCostData pricingDetails, ShopAddressData addressData, Boolean isOpen, String averageDeliveryTime, String bannerImage, String averageRatings, String shopType, String reviews, String menuItemsID) {
        this.name = name;
        this.tagLine = tagLine;
        this.pricingDetails = pricingDetails;
        this.addressData = addressData;
        this.isOpen = isOpen;
        this.averageDeliveryTime = averageDeliveryTime;
        this.bannerImage = bannerImage;
        this.averageRatings = averageRatings;
        this.shopType = shopType;
        this.reviews = reviews;
        this.menuItemsID = menuItemsID;
    }

    public ProcessingFee getProcessingFees() {
        return processingFees;
    }

    public boolean isAllowCOD() {
        return allowCOD;
    }

    public boolean isAllowSelfPickup() {
        return allowSelfPickup;
    }

    public String getShopTimings() {
        return shopTimings;
    }

    public boolean isAllowProcessingFees() {
        return allowProcessingFees;
    }

    public String getFssaiCode() {
        return fssaiCode;
    }

    public int getIncreaseDisplayPricePercentage() {
        return increaseDisplayPricePercentage;
    }

    public boolean isAdminShopService() {
        return adminShopService;
    }

    public boolean isAllowCheckout() {
        return allowCheckout;
    }

    public String getTaxPercentage() {
        return taxPercentage;
    }

    public String get_id() {
        return _id;
    }

    public String getMenuItemsID() {
        return menuItemsID;
    }

    public String getReviews() {
        return reviews;
    }

    public String getName() {
        return name;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public String getAverageRatings() {
        return averageRatings;
    }

    public String getShopType() {
        return shopType;
    }

    public String getShopName() {
        return name;
    }

    public String getTagLine() {
        return tagLine;
    }

    public ShopCostData getPricingDetails() {
        return pricingDetails;
    }

    public ShopAddressData getAddressData() {
        return addressData;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public String getAverageDeliveryTime() {
        return averageDeliveryTime;
    }
}
