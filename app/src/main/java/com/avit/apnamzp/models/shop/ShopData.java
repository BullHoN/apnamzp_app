package com.avit.apnamzp.models.shop;

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
